package com.srct.service.account.service;

import com.srct.service.account.vo.iot.IotBaseReq;
import com.srct.service.account.vo.iot.IotBaseResp;
import com.srct.service.account.vo.iot.IotFetchTokenResp;
import com.srct.service.account.vo.iot.IotTokenConfirmReq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: IotTokenService
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/3 16:59
 * @description Project Name: Grote
 * @Package: com.srct.service.account.service
 */
public interface IotTokenService {
    /**
     * 验证IOT设备请求token有效性
     *
     * @param request
     * @param response
     */
    void validate(HttpServletRequest request, HttpServletResponse response);

    /**
     * 获取IOT token状态，作为返回参数
     *
     * @return
     */
    String getTokenUpdateSwitch();

    /**
     * 生成iot token
     * 注意同时设定期望更新时间戳TokenItemConst.IOT_TOKEN_NEED_UPDATE_TIMESTAMP
     *
     * @param iotBaseReq
     * @return
     */
    IotFetchTokenResp fetchToken(IotBaseReq iotBaseReq);

    /**
     * 确认token更换成功
     * @param tokenConfirmReq
     * @return
     */
    IotBaseResp confirm(IotTokenConfirmReq tokenConfirmReq);
}
