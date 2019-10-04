package com.srct.service.account.service.impl;

import com.srct.service.account.constants.account.AccountParamConst;
import com.srct.service.account.constants.token.TokenItemConst;
import com.srct.service.account.dao.entity.User;
import com.srct.service.account.dao.repository.UserService;
import com.srct.service.account.service.IotTokenService;
import com.srct.service.account.service.TokenService;
import com.srct.service.account.vo.iot.IotBaseReq;
import com.srct.service.account.vo.iot.IotBaseResp;
import com.srct.service.account.vo.iot.IotFetchTokenResp;
import com.srct.service.account.vo.iot.IotTokenConfirmReq;
import com.srct.service.constant.CommonConst;
import com.srct.service.constant.ErrCodeSys;
import com.srct.service.service.cache.FrameCacheService;
import com.srct.service.utils.DateUtils;
import com.srct.service.utils.HttpUtil;
import com.srct.service.utils.StringUtil;
import com.srct.service.validate.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Title: IotTokenServiceImpl
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/3 17:00
 * @description Project Name: Grote
 * @Package: com.srct.service.account.service.impl
 */
@Slf4j
@Service
public class IotTokenServiceImpl implements IotTokenService {

    @Resource
    private UserService userService;
    @Resource
    private TokenService tokenService;
    @Resource
    private FrameCacheService frameCacheService;

    @Override
    public void validate(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        IotBaseReq iotBaseReq = HttpUtil.getParamMap(request, IotBaseReq.class);
        traceRequest(method, url, queryString, iotBaseReq);
        validateToken(iotBaseReq);
    }

    @Override
    public String getTokenUpdateSwitch() {
        Date refreshTime = tokenService.getItem(TokenItemConst.IOT_TOKEN_NEED_UPDATE_TIMESTAMP.name(), Date.class);
        String updateSwitch = CommonConst.NO;
        if (refreshTime == null) {
            return updateSwitch;
        }
        updateSwitch = new Date().after(refreshTime) ? CommonConst.YES : CommonConst.NO;
        if (StringUtil.convertSwitch(updateSwitch)) {
            String token = tokenService.buildIotToken();
            tokenService.putItem(TokenItemConst.IOT_UPDATE_TOKEN.name(), token);
        }
        return updateSwitch;
    }

    @Override
    public IotFetchTokenResp fetchToken(IotBaseReq iotBaseReq) {
        User user = userService.getUserByGuid(iotBaseReq.getDeviceId());
        if (user != null) {
            Validator.assertNo(user.getStatus(), ErrCodeSys.SYS_ERR_MSG, "请管理员重置设备以重新上线");
        }
        String token = tokenService.buildIotToken();
        updateUserLoginTime(iotBaseReq.getDeviceId());
        int updatedExpired = frameCacheService.getParamInt(AccountParamConst.IOT_TOKEN_UPDATE_EXPIRED);
        Date updateStamp = DateUtils.addSeconds(new Date(), updatedExpired);
        tokenService.putItem(token, TokenItemConst.IOT_TOKEN_NEED_UPDATE_TIMESTAMP.name(), updateStamp);
        tokenService.putItem(token, TokenItemConst.IOT_DEVICE_ID.name(), iotBaseReq.getDeviceId());
        return buildIotFetchTokenResp(token);
    }

    @Override
    public IotBaseResp confirm(IotTokenConfirmReq tokenConfirmReq) {
        String updateToken = tokenService.getItem(TokenItemConst.IOT_UPDATE_TOKEN.name(),
                tokenConfirmReq.getTokenConfirmed(), String.class);
        Validator.assertTrue(StringUtils.equals(tokenConfirmReq.getTokenConfirmed(), updateToken),
                ErrCodeSys.PA_DATA_DIFF, "更新token");
        updateUserLoginTime(tokenConfirmReq.getDeviceId());
        tokenService.setTokenValue(updateToken, tokenService.getTokenValue());
        tokenService.removeToken();
        return new IotBaseResp();
    }

    private void updateUserLoginTime(String guid) {
        User user = userService.getUserByGuid(guid);
        user.setLastLoginTime(new Date());
        user.setStatus(CommonConst.YES);
        userService.updateByPrimaryKeySelective(user);
    }

    private IotFetchTokenResp buildIotFetchTokenResp(String token) {
        IotFetchTokenResp resp = new IotFetchTokenResp();
        resp.setAuthToken(token);
        resp.setTokenUpdateSwitch(CommonConst.NO);
        return resp;
    }

    private void traceRequest(String method, String url, String queryString, IotBaseReq iotInfo) {
        log.info("[{}] {}?{} -> {}-{}}", method, url, queryString, iotInfo.getDeviceId(), iotInfo.getToken());
    }

    private void validateToken(IotBaseReq iotBaseReq) {
        String token = iotBaseReq.getToken();
        Validator.assertTrue(tokenService.isTokenExist(token), ErrCodeSys.SYS_SESSION_TIME_OUT, "token");
        String deviceId = tokenService.getItem(token, TokenItemConst.IOT_DEVICE_ID.name(), String.class);
        Validator.assertTrue(StringUtils.equals(iotBaseReq.getDeviceId(), deviceId), ErrCodeSys.PA_DATA_DIFF,
                "设备标识");
        tokenService.setToken(token);
    }
}
