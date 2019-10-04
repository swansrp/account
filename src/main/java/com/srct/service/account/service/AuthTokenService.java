package com.srct.service.account.service;

import com.srct.service.config.annotation.Auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Title: AuthTokenService
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/3 16:58
 * @description Project Name: Grote
 * @Package: com.srct.service.account.service
 */
public interface AuthTokenService {
    /**
     * 验证app/web请求权限
     *
     * @param request
     * @param response
     * @param authType
     */
    void validate(HttpServletRequest request, HttpServletResponse response, Auth.AuthType authType);

    /**
     * 跨域头设置
     *
     * @param response
     */
    void enableCrossDomain(HttpServletResponse response);
}
