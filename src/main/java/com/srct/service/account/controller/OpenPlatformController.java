package com.srct.service.account.controller;

import com.srct.service.account.constants.common.RequestConst;
import com.srct.service.account.service.LoginService;
import com.srct.service.account.utils.AuthStringUtil;
import com.srct.service.account.vo.platform.OpenPlatformAccessTokenResp;
import com.srct.service.account.vo.platform.OpenPlatformRegResp;
import com.srct.service.config.annotation.Auth;
import com.srct.service.config.response.CommonExceptionHandler;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.utils.HttpUtil;
import com.srct.service.utils.security.Base64Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: OpenPlatformController
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/9 10:17
 * @description Project Name: Grote
 * @Package: com.srct.service.account.controller
 */
@Auth(role = Auth.AuthType.UNLOGIN)
@Api(value = "公共平台相关接口", tags = "公共平台相关接口")
@RestController("OpenPlatformController")
@RequestMapping(value = "/platform")
public class OpenPlatformController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private LoginService loginService;

    @ApiOperation(value = "获取接入AccessToken", notes = "获取接入AccessToken")
    @RequestMapping(value = "/accessToken", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<OpenPlatformAccessTokenResp>.Resp> fetchToken() {
        Map<String, String> authMap = AuthStringUtil.decodeAuthorization(request);
        String appKey = authMap.get(RequestConst.APP_KEY);
        String appSecret = authMap.get(RequestConst.APP_SECRET);
        OpenPlatformAccessTokenResp res = loginService.getOpenPlatformAccessToken(appKey, appSecret);
        return CommonExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "新增接入id", notes = "获取接入AccessToken")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<OpenPlatformRegResp>.Resp> register() {
        OpenPlatformRegResp res = loginService.registerOpenPlatform();
        return CommonExceptionHandler.generateResponse(res);
    }

    @Auth(role = Auth.AuthType.GUEST)
    @ApiOperation(value = "获取接入秘钥", notes = "获取接入秘钥")
    @RequestMapping(value = "/secret", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "appKey", value = "获取秘钥的appKey")})
    public ResponseEntity<CommonResponse<OpenPlatformRegResp>.Resp> getAppSecret(String appKey) {
        if (StringUtils.isEmpty(appKey)) {
            Map<String, String> authMap = AuthStringUtil.decodeAuthorization(request);
            appKey = authMap.get(RequestConst.APP_KEY);
        }
        OpenPlatformRegResp res = loginService.getOpenPlatformAppSecret(appKey);
        return CommonExceptionHandler.generateResponse(res);
    }
}
