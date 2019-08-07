/**
 * Title: LoginServiceImpl.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 17:56
 * @description Project Name: Grote
 * @Package: com.srct.service.account.service.impl
 */
package com.srct.service.account.service.impl;

import com.srct.service.account.bo.permit.PermitBO;
import com.srct.service.account.constants.account.AccountErrCode;
import com.srct.service.account.constants.account.AccountParamConst;
import com.srct.service.account.constants.account.AccountSeqConst;
import com.srct.service.account.constants.common.ClientTypeConst;
import com.srct.service.account.constants.token.TokenItemConst;
import com.srct.service.account.dao.entity.User;
import com.srct.service.account.dao.entity.UserRole;
import com.srct.service.account.dao.repository.UserRoleService;
import com.srct.service.account.dao.repository.UserService;
import com.srct.service.account.service.LoginService;
import com.srct.service.account.service.PermitCoreService;
import com.srct.service.account.service.TokenService;
import com.srct.service.account.vo.login.LoginRes;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.holder.ClientTypeHolder;
import com.srct.service.constant.CommonConst;
import com.srct.service.constant.ErrCodeSys;
import com.srct.service.dao.repository.CommonSequenceService;
import com.srct.service.service.cache.FrameCacheService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.log.Log;
import com.srct.service.utils.security.MD5Util;
import com.srct.service.utils.security.RandomUtil;
import com.srct.service.validate.Validator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserService userService;
    @Resource
    private FrameCacheService frameCacheService;
    @Resource
    private TokenService tokenService;
    @Resource
    private PermitCoreService permitCoreService;
    @Resource
    private CommonSequenceService commonSeqService;
    @Resource
    private UserRoleService userRoleService;

    @Override
    public LoginRes login(String id, String password) {
        User user = userService.getUserByUserId(id);
        if (user == null) {
            user = userService.getUserByPhoneNumber(id);
        }
        if (user == null) {
            user = userService.getUserByEmail(id);
        }
        Validator.assertNotNull(user, AccountErrCode.AC_USER_NOT_EXISTED);
        Validator.assertYes(user.getStatus(), AccountErrCode.AC_LOCK);
        verifyPassword(user, password);
        userService.updateByPrimaryKeySelective(user);
        return buildLoginRes(user);
    }

    @Override
    public LoginRes login(String phoneNumber) {
        User user = userService.getUserByPhoneNumber(phoneNumber);
        if (user == null) {
            user = createUser(phoneNumber);
        } else {
            user.setStatus(CommonConst.YES);
            user.setPasswordErrorTimes(0);
            userService.updateByPrimaryKeySelective(user);
        }
        Log.ii(user);
        return buildLoginRes(user);
    }

    @Override
    public LoginRes refreshLogin(String refreshToken) {
        Validator.assertTrue(tokenService.isTokenExist(refreshToken), ErrCodeSys.SYS_SESSION_TIME_OUT);
        String userId = tokenService.getItem(refreshToken, TokenItemConst.OPERATOR.name(), String.class);
        Validator.assertNotBlank(userId, ErrCodeSys.PA_DATA_NOT_EXIST, "用户");
        User user = userService.getUserByUserId(userId);
        Validator.assertNotNull(user, AccountErrCode.AC_USER_NOT_EXISTED);
        Validator.assertYes(user.getStatus(), AccountErrCode.AC_LOCK);
        tokenService.removeToken(refreshToken);
        return buildLoginRes(user);
    }

    @Override
    public LoginRes register(String userId, String password) {
        User user = userService.getUserByUserId(userId);
        Validator.assertNull(user, AccountErrCode.AC_USER_ALREADY_EXISTED);
        user = createUser(userId, password);
        return buildLoginRes(user);
    }

    private void bindDefaultRole(User user) {
        Integer defaultRoleId = frameCacheService.getParamInt(AccountParamConst.ACCOUNT_DEFAULT_ROLE_ID);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(defaultRoleId);
        userRole.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        userRoleService.insertOrUpdate(userRole);
    }

    private String buildAccessToken(String userId) {
        String accessToken = null;
        String clientType = getClientType();
        if (clientType.equals(ClientTypeConst.APP.name())) {
            accessToken = tokenService.buildAppAccessToken();
        } else if (clientType.equals(ClientTypeConst.WEB.name())) {
            accessToken = tokenService.buildWebAccessToken();
        } else if (clientType.equals(ClientTypeConst.WECHAT.name())) {
            accessToken = tokenService.buildWechatToken();
        }
        tokenService.putItem(accessToken, TokenItemConst.OPERATOR.name(), userId);
        return accessToken;
    }

    private User buildBaseUser() {
        User user = new User();
        user.setGuid(RandomUtil.getUUID());
        user.setCustomerNo(commonSeqService.getSeq(AccountSeqConst.CUSTOMER_NUMBER_SEQ.name()));
        user.setStatus(CommonConst.YES);
        user.setPasswordErrorTimes(0);
        user.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return user;
    }

    private LoginRes buildLoginRes(User user) {
        LoginRes res = new LoginRes();
        BeanUtil.copyProperties(user, res);
        fillToken(res);
        return res;
    }

    private void buildPermitTree(String userId, String accessToken) {
        String clientType = getClientType();
        List<PermitBO> permitList = permitCoreService.getPermitList(userId, clientType);
        Validator.assertNotEmpty(permitList, AccountErrCode.AC_NO_PERMIT_TREE);
        tokenService.putItem(accessToken, TokenItemConst.PERMIT_TREE.name(), permitList);
    }

    private String buildRefreshToken(String userId) {
        String refreshToken = null;
        String clientType = getClientType();
        if (clientType.equals(ClientTypeConst.APP.name())) {
            refreshToken = tokenService.buildAppRefreshToken();
        } else if (clientType.equals(ClientTypeConst.WEB.name())) {
            refreshToken = tokenService.buildWebRefreshToken();
        }
        tokenService.putItem(refreshToken, TokenItemConst.OPERATOR.name(), userId);
        return refreshToken;
    }

    private User createUser(String phoneNumber) {
        User user = buildBaseUser();
        user.setPhone(phoneNumber);
        user.setUserId(phoneNumber);
        userService.insertOrUpdateSelective(user);
        bindDefaultRole(user);
        return user;
    }

    private User createUser(String userId, String password) {
        User user = buildBaseUser();
        user.setUserId(userId);
        user.setPassword(MD5Util.generate(password));
        userService.insertOrUpdateSelective(user);
        bindDefaultRole(user);
        return user;
    }

    private void fillToken(LoginRes res) {
        String accessToken = buildAccessToken(res.getUserId());
        String refreshToken = buildRefreshToken(res.getUserId());
        res.setAccessToken(accessToken);
        res.setRefreshToken(refreshToken);
        buildPermitTree(res.getUserId(), accessToken);
    }

    private String getClientType() {
        String clientType = ClientTypeHolder.get();
        Validator.assertNotNull(clientType, ErrCodeSys.PA_DATA_NOT_EXIST, "客户端类型");
        return clientType;
    }

    private void verifyPassword(User user, String password) {
        if (MD5Util.verify(password, user.getPassword())) {
            user.setStatus(CommonConst.YES);
            user.setPasswordErrorTimes(0);
        } else {
            int passwordMistakeMaxTime = frameCacheService.getParamInt(AccountParamConst.ACCOUNT_LOCK_MISTAKE_NUMBER);
            user.setPasswordErrorTimes(user.getPasswordErrorTimes() + 1);
            if (user.getPasswordErrorTimes() >= passwordMistakeMaxTime) {
                user.setStatus(CommonConst.NO);
            }
            user.setPasswordErrorLastTime(new Date());
            userService.updateByPrimaryKeySelective(user);
            Validator.assertException(AccountErrCode.AC_PASSWORD_MISTAKE);
        }
    }
}
