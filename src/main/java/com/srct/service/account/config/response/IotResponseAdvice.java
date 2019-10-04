package com.srct.service.account.config.response;

import com.srct.service.account.service.IotTokenService;
import com.srct.service.account.vo.iot.IotBaseResp;
import com.srct.service.config.annotation.Auth;
import com.srct.service.config.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;

/**
 * Title: IotResponseAdvice
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/3 20:24
 * @description Project Name: Grote
 * @Package: com.srct.service.account.config.response
 */
@Slf4j
@ControllerAdvice
public class IotResponseAdvice implements ResponseBodyAdvice {

    @Resource
    private IotTokenService iotTokenService;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        if (methodParameter.getMethodAnnotation(Auth.class) != null) {
            return methodParameter.getMethodAnnotation(Auth.class).role().equals(Auth.AuthType.IOT);
        }
        if (methodParameter.getDeclaringClass().isAnnotationPresent(Auth.class)) {
            return methodParameter.getDeclaringClass().getAnnotation(Auth.class).role().equals(Auth.AuthType.IOT);
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (o instanceof CommonResponse.Resp) {
            if (((CommonResponse.Resp) o).data instanceof IotBaseResp) {
                String updateSwitch = iotTokenService.getTokenUpdateSwitch();
                ((IotBaseResp) ((CommonResponse.Resp) o).data).setTokenUpdateSwitch(updateSwitch);
            }
        }
        return o;
    }
}
