/**
 * Title: LoginController.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 17:39
 * @description Project Name: Grote
 * @Package: com.srct.service.account.controller
 */
package com.srct.service.account.controller;

import com.srct.service.account.service.LoginService;
import com.srct.service.account.service.TokenService;
import com.srct.service.account.utils.TokenTypeUtil;
import com.srct.service.account.vo.login.LoginReq;
import com.srct.service.account.vo.login.LoginRes;
import com.srct.service.account.vo.login.MsgLoginReq;
import com.srct.service.cache.constant.MsgVerificationType;
import com.srct.service.config.annotation.Auth;
import com.srct.service.config.holder.ClientTypeHolder;
import com.srct.service.config.response.CommonExceptionHandler;
import com.srct.service.config.response.CommonResponse;
import com.srct.service.constant.ErrCodeSys;
import com.srct.service.service.CaptchaService;
import com.srct.service.service.sms.MsgVerificationService;
import com.srct.service.validate.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.srct.service.config.annotation.Auth.AuthType.GUEST;
import static com.srct.service.config.annotation.Auth.AuthType.UNLOGIN;

@Auth(role = UNLOGIN)
@Api(value = "登录操作", tags = "登录操作")
@RestController("LoginController")
@RequestMapping(value = "")
public class LoginController {

    @Resource
    private TokenService tokenService;
    @Resource
    private LoginService loginService;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private MsgVerificationService msgVerificationService;


    @ApiOperation(value = "用户登入", notes = "利用账号密码登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "id", value = "用户名或者手机号或者邮箱", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "password", value = "密码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "graphCode", value = "图形验证码", required = true)})
    public ResponseEntity<CommonResponse<LoginRes>.Resp> login(LoginReq loginReq) {
        String token = tokenService.getToken();
        captchaService.validateCaptcha(token, loginReq.getGraphCode());
        LoginRes res = loginService.login(loginReq.getId(), loginReq.getPassword());
        return CommonExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "用户注册", notes = "利用短信验证码注册")
    @RequestMapping(value = "/login/msg", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "phoneNumber", value = "手机号码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "loginMsgCode", value = "短信验证码", required = true)})
    public ResponseEntity<CommonResponse<LoginRes>.Resp> login(MsgLoginReq msgLoginReq) {
        String token = tokenService.getToken();
        msgVerificationService
                .validateMsgCode(token, msgLoginReq.getLoginMsgCode(), MsgVerificationType.LOGIN_MSG_CODE);
        LoginRes res = loginService.login(msgLoginReq.getPhoneNumber());
        return CommonExceptionHandler.generateResponse(res);
    }

    @Auth(role = Auth.AuthType.NONE)
    @ApiOperation(value = "用户REFRESH TOKEN登录", notes = "利用refresh token登录")
    @RequestMapping(value = "/login/refresh", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "refreshToken", value = "刷新验证码", required = true)})
    public ResponseEntity<CommonResponse<LoginRes>.Resp> login(String refreshToken) {
        String clientType = ClientTypeHolder.get();
        String tokenType = tokenService.getTokenType(refreshToken);
        Validator.assertTrue(tokenType.startsWith(clientType), ErrCodeSys.PA_DATA_DIFF, "TOKEN类型");
        LoginRes res = loginService.refreshLogin(refreshToken);
        return CommonExceptionHandler.generateResponse(res);
    }

    @ApiOperation(value = "用户注册", notes = "利用账号密码注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "id", value = "用户名", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "password", value = "密码", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "graphCode", value = "图形验证码", required = true)})
    public ResponseEntity<CommonResponse<LoginRes>.Resp> register(LoginReq loginReq) {
        String token = tokenService.getToken();
        captchaService.validateCaptcha(token, loginReq.getGraphCode());
        LoginRes res = loginService.register(loginReq.getId(), loginReq.getPassword());
        return CommonExceptionHandler.generateResponse(res);
    }

    @Auth(role = GUEST)
    @ApiOperation(value = "退出登录", notes = "退出登录")
    @RequestMapping(value = "/logoff", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<String>.Resp> logoff() {
        String accessToken = tokenService.getToken();
        String tokenType = TokenTypeUtil.getTokenTypeClientType(ClientTypeHolder.get());
        Validator.assertTrue(accessToken.startsWith(tokenType), ErrCodeSys.PA_DATA_DIFF, "token类型");
        tokenService.removeToken(accessToken);
        return CommonExceptionHandler.generateResponse("");
    }


}
