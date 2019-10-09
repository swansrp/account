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
import com.srct.service.account.dao.common.entity.User;
import com.srct.service.account.dao.common.entity.UserRole;
import com.srct.service.account.dao.common.repository.UserRoleService;
import com.srct.service.account.dao.common.repository.UserService;
import com.srct.service.account.service.LoginService;
import com.srct.service.account.service.PermitCoreService;
import com.srct.service.account.service.TokenService;
import com.srct.service.account.vo.login.LoginRes;
import com.srct.service.account.vo.platform.OpenPlatformAccessTokenResp;
import com.srct.service.account.vo.platform.OpenPlatformRegResp;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.config.holder.ClientTypeHolder;
import com.srct.service.constant.CommonConst;
import com.srct.service.constant.ErrCodeSys;
import com.srct.service.dao.repository.CommonSequenceService;
import com.srct.service.service.cache.FrameCacheService;
import com.srct.service.utils.BeanUtil;
import com.srct.service.utils.security.Base64Util;
import com.srct.service.utils.security.DesUtil;
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
        verifyPassword(user, user.getPassword(), password);
        userService.updateByPrimaryKeySelective(user);
        return buildLoginRes(user);
    }

    private void verifyPassword(User user, String passwordMd5, String password) {
        if (MD5Util.verify(password, passwordMd5)) {
            setUserLoginSuccessfully(user);
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

    private LoginRes buildLoginRes(User user) {
        LoginRes res = new LoginRes();
        BeanUtil.copyProperties(user, res);
        fillToken(res);
        return res;
    }

    private void setUserLoginSuccessfully(User user) {
        user.setStatus(CommonConst.YES);
        user.setPasswordErrorTimes(0);
        user.setLastLoginTime(new Date());
    }

    private void fillToken(LoginRes res) {
        String accessToken = buildAccessToken(res.getUserId());
        String refreshToken = buildRefreshToken(res.getUserId());
        res.setAccessToken(accessToken);
        res.setRefreshToken(refreshToken);
        buildPermitTree(res.getUserId(), accessToken);
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

    private void buildPermitTree(String userId, String accessToken) {
        String clientType = getClientType();
        List<PermitBO> permitList = permitCoreService.getPermitList(userId, clientType);
        Validator.assertNotEmpty(permitList, AccountErrCode.AC_NO_PERMIT_TREE);
        tokenService.putItem(accessToken, TokenItemConst.PERMIT_TREE.name(), permitList);
    }

    private String getClientType() {
        String clientType = ClientTypeHolder.get();
        Validator.assertNotNull(clientType, ErrCodeSys.PA_DATA_NOT_EXIST, "客户端类型");
        return clientType;
    }

    @Override
    public LoginRes loginOrReg(String phoneNumber) {
        User user = userService.getUserByPhoneNumber(phoneNumber);
        if (user == null) {
            user = createUser(phoneNumber);
        } else {
            setUserLoginSuccessfully(user);
            userService.updateByPrimaryKeySelective(user);
        }
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

    private User createUser(String userId, String password) {
        User user = buildBaseUser();
        user.setUserId(userId);
        user.setPassword(MD5Util.generate(password));
        userService.insertOrUpdateSelective(user);
        bindDefaultRole(user);
        return user;
    }

    @Override
    public User createUserWithName(String phoneNumber, String name) {
        User user = buildBaseUser();
        user.setName(name);
        user.setPhone(phoneNumber);
        user.setUserId(phoneNumber);
        userService.insertOrUpdateSelective(user);
        bindDefaultRole(user);
        return user;
    }

    @Override
    public OpenPlatformRegResp registerOpenPlatform() {
        User user = buildBaseUser();
        String appKey = RandomUtil.getUUID();
        String appSecret = RandomUtil.getUUID();
        String encrypt = encryptAppSecret(appKey, appSecret);
        user.setUserId(appKey);
        user.setAppKey(appKey);
        user.setAppSecret(encrypt);
        userService.insertOrUpdateSelective(user);
        return buildOpenPlatformRegResp(appKey, appSecret);
    }

    @Override
    public OpenPlatformRegResp getOpenPlatformAppSecret(String appKey) {
        User user = userService.getUserByAppKey(appKey);
        String appSecret = RandomUtil.getUUID();
        String encrypt = encryptAppSecret(appKey, appSecret);
        user.setAppSecret(encrypt);
        userService.insertOrUpdate(user);
        return buildOpenPlatformRegResp(appKey, appSecret);
    }

    @Override
    public OpenPlatformAccessTokenResp getOpenPlatformAccessToken(String appKey, String appSecret) {
        User user = userService.getUserByAppKey(appKey);
        Validator.assertNotNull(user, AccountErrCode.AC_USER_NOT_EXISTED);
        Validator.assertYes(user.getStatus(), AccountErrCode.AC_LOCK);
        String encrypt = DesUtil.encrypt(appKey, appSecret);
        verifyPassword(user, user.getAppSecret(), encrypt);
        String token = tokenService.buildOpenPlatformToken();
        tokenService.putItem(token, TokenItemConst.OPERATOR.name(), appKey);
        return buildOpenPlatformAccessTokenResp(appKey, token);
    }

    private OpenPlatformAccessTokenResp buildOpenPlatformAccessTokenResp(String appKey, String token) {
        OpenPlatformAccessTokenResp resp = new OpenPlatformAccessTokenResp();
        resp.setAccessToken(token);
        resp.setAppKey(appKey);
        return resp;
    }

    private String encryptAppSecret(String appKey, String appSecret) {
        String des = DesUtil.encrypt(appKey, appSecret);
        String encrypt = MD5Util.generate(des);
        return encrypt;
    }

    private OpenPlatformRegResp buildOpenPlatformRegResp(String appKey, String appSecret) {
        OpenPlatformRegResp res = new OpenPlatformRegResp();
        res.setAppKey(appKey);
        res.setAppSecret(appSecret);
        String authString = "basic " + Base64Util.encode(appKey + ":" + appSecret);
        res.setAuthString(authString);
        return res;
    }

    private User createUser(String phoneNumber) {
        return createUserWithName(phoneNumber, null);
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

    private void bindDefaultRole(User user) {
        Integer defaultRoleId = frameCacheService.getParamInt(AccountParamConst.ACCOUNT_DEFAULT_ROLE_ID);
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(defaultRoleId);
        userRole.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        userRoleService.insertOrUpdate(userRole);
    }
}
