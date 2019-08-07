/**
 * Title: AuthTokenServiceImpl.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-27 20:47
 * @description Project Name: Grote
 * @package: com.srct.service.account.service.impl
 */
package com.srct.service.account.service.impl;

import com.srct.service.account.constants.common.RequestConst;
import com.srct.service.account.dao.entity.Permit;
import com.srct.service.account.provider.TokenItemProvider;
import com.srct.service.account.service.TokenService;
import com.srct.service.account.utils.TokenTypeUtil;
import com.srct.service.config.annotation.Auth;
import com.srct.service.config.holder.ClientTypeHolder;
import com.srct.service.constant.ErrCodeSys;
import com.srct.service.dao.entity.ServiceApi;
import com.srct.service.service.AuthTokenService;
import com.srct.service.service.cache.FrameCacheService;
import com.srct.service.utils.HttpUtil;
import com.srct.service.utils.JSONUtil;
import com.srct.service.utils.StringUtil;
import com.srct.service.validate.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AuthTokenServiceImpl implements AuthTokenService {

    @Resource
    private FrameCacheService frameCacheService;
    @Resource
    private TokenService tokenService;
    @Resource
    private TokenItemProvider tokenItem;
    @Resource
    private HttpServletRequest request;

    @Override
    public void validate(HttpServletRequest request, HttpServletResponse response, Auth.AuthType authType) {
        enableCrossDomain(response);
        String url = request.getRequestURI();
        String method = request.getMethod();
        String queryString = request.getQueryString();
        Map<String, String> headersInfoMap = HttpUtil.getHeadersInfoMap(request);
        ClientTypeHolder.set(headersInfoMap.get(RequestConst.CLIENT_TYPE));
        if (!Auth.AuthType.NONE.equals(authType)) {
            authType = updateAuthTypeByServiceApi(method, url, headersInfoMap);
        }
        String userId = headersInfoMap.get(RequestConst.USER_ID);
        traceRequest(method, url, queryString, userId, authType);
        validateAuthType(method, url, headersInfoMap, authType);
    }

    private void traceRequest(String method, String url, String queryString, String userId, Auth.AuthType authType) {
        log.debug("[{}] {}?{} -> {}-{}", method, url, queryString, userId, authType);
    }

    private void validateAuthType(String method, String url, Map<String, String> headersInfoMap,
            Auth.AuthType authType) {
        switch (authType) {
            case GUEST:
                validateLogin(headersInfoMap);
                break;
            case USER:
                validatePermit(method, url, headersInfoMap);
                break;
            case UNLOGIN:
                validateToken(headersInfoMap);
                break;
            default:
                break;
        }
    }

    private void validateToken(Map<String, String> headersInfoMap) {
        String token = headersInfoMap.get(RequestConst.TOKEN);
        Validator.assertNotBlank(token, ErrCodeSys.SYS_SESSION_TIME_OUT);
        tokenService.setToken(token);
    }

    private void validatePermit(String method, String url, Map<String, String> headersInfoMap) {
        validateLogin(headersInfoMap);
        String token = headersInfoMap.get(RequestConst.TOKEN);
        String apiVersion = headersInfoMap.get(RequestConst.API_VERSION);
        ServiceApi serviceApi = frameCacheService.getServiceApi(method, url, apiVersion);
        List<String> permitIdList = frameCacheService.getPermitIdList(serviceApi.getApiId());
        String permitTreeString = tokenService.getItem(token, tokenItem.getPemritTree(), String.class);
        List<Permit> permitList = JSONUtil.readJson(permitTreeString, List.class, Permit.class);
        boolean hasPermit = hasPermit(permitIdList, permitList);
        Validator.assertTrue(hasPermit, ErrCodeSys.SYS_PERMIT_ERROR, "接口" + serviceApi.getUrl() + "没有访问权限");
    }

    private boolean hasPermit(List<String> permitIdList, List<Permit> permitList) {
        if (permitIdList != null && permitList != null) {
            for (Permit permit : permitList) {
                if (permitIdList.contains(permit.getId().toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void validateLogin(Map<String, String> headersInfoMap) {
        validateLoginToken(headersInfoMap);
        String token = headersInfoMap.get(RequestConst.TOKEN);
        String userId = headersInfoMap.get(RequestConst.USER_ID);
        Validator.assertNotBlank(userId, ErrCodeSys.PA_PARAM_NULL, "登录用户名");
        String operator = tokenService.getItem(token, tokenItem.getOperator(), String.class);
        Validator.assertMatch(userId, operator, ErrCodeSys.SYS_SESSION_NOT_SAME);
    }

    private void validateLoginToken(Map<String, String> headersInfoMap) {
        String token = headersInfoMap.get(RequestConst.TOKEN);
        String tokenType = TokenTypeUtil.getTokenTypeClientType(ClientTypeHolder.get());
        Validator.assertNotBlank(tokenType, ErrCodeSys.PA_PARAM_FORMAT, "token类型");
        Validator.assertTrue(token.startsWith(tokenType), ErrCodeSys.PA_DATA_DIFF, "token类型");
        tokenService.setToken(token);
    }

    private void enableCrossDomain(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    private Auth.AuthType updateAuthTypeByServiceApi(String method, String url, Map<String, String> headerMap) {
        String apiVersion = MapUtils.getString(headerMap, RequestConst.API_VERSION);
        ServiceApi serviceApi = frameCacheService.getServiceApi(method, url, apiVersion);
        if (StringUtil.convertSwitch(serviceApi.getNeedPermit())) {
            return Auth.AuthType.USER;
        } else if (StringUtil.convertSwitch(serviceApi.getNeedLogin())) {
            return Auth.AuthType.GUEST;
        } else if (StringUtil.convertSwitch(serviceApi.getNeedToken())) {
            return Auth.AuthType.UNLOGIN;
        }
        return Auth.AuthType.NONE;
    }
}
