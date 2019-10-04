package com.srct.service.account.controller;

import com.srct.service.account.service.IotTokenService;
import com.srct.service.account.vo.iot.IotBaseReq;
import com.srct.service.account.vo.iot.IotBaseResp;
import com.srct.service.account.vo.iot.IotFetchTokenResp;
import com.srct.service.account.vo.iot.IotTokenConfirmReq;
import com.srct.service.config.annotation.Auth;
import com.srct.service.config.response.CommonExceptionHandler;
import com.srct.service.config.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.srct.service.config.annotation.Auth.AuthType.IOT;
import static com.srct.service.config.annotation.Auth.AuthType.NONE;

/**
 * Title: IotController
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/3 20:27
 * @description Project Name: Grote
 * @Package: com.srct.service.account.controller
 */
@Auth(role = IOT)
@Api(value = "IOT token", tags = "iot设备基本操作")
@RestController("IotController")
@RequestMapping(value = "/iot")
public class IotController {

    @Resource
    private IotTokenService iotTokenService;

    @ApiOperation(value = "do something", notes = "业务接口")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<IotBaseResp>.Resp> doSomething(IotTokenConfirmReq tokenConfirmReq) {
        return CommonExceptionHandler.generateResponse(null);
    }

    @ApiOperation(value = "确认token变更", notes = "向服务器确认token更新,废除老token,使新token生效")
    @RequestMapping(value = "/token/confirm", method = RequestMethod.POST)
    public ResponseEntity<CommonResponse<IotBaseResp>.Resp> confirm(IotTokenConfirmReq tokenConfirmReq) {
        IotBaseResp res = iotTokenService.confirm(tokenConfirmReq);
        return CommonExceptionHandler.generateResponse(res);
    }

    @Auth(role = NONE)
    @ApiOperation(value = "获取token", notes = "初始化过程中使用guid获取token")
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<IotFetchTokenResp>.Resp> fetchToken(IotBaseReq iotBaseReq) {
        IotFetchTokenResp res = iotTokenService.fetchToken(iotBaseReq);
        return CommonExceptionHandler.generateResponse(res);
    }
}
