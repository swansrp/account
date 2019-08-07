/**
 * Title: TokenServiceImpl.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 18:05
 * @description Project Name: Grote
 * @Package: com.srct.service.account.service.impl
 */
package com.srct.service.account.service.impl;

import com.srct.service.account.constants.account.AccountParamConst;
import com.srct.service.account.constants.token.TokenTypeConst;
import com.srct.service.account.service.TokenService;
import com.srct.service.config.holder.TokenHolder;
import com.srct.service.constant.ErrCodeSys;
import com.srct.service.service.RedisService;
import com.srct.service.service.cache.FrameCacheService;
import com.srct.service.utils.JSONUtil;
import com.srct.service.utils.security.RandomUtil;
import com.srct.service.validate.Validator;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {

    private static final String BASE_KEY = "Token:";
    private static final String SEPARATION = ":";
    private static final String TIMESTAMP = "TIMESTAMP";
    private static final String EXPIRED_PARAM_SUFFIX = "_EXPIRED";

    @Resource
    private RedisService redisService;
    @Resource
    private FrameCacheService frameCacheService;

    @Override
    public String buildAppAccessToken() {
        return buildToken(TokenTypeConst.APP_ACCESS_TOKEN);
    }

    @Override
    public String buildAppRefreshToken() {
        return buildToken(TokenTypeConst.APP_REFRESH_TOKEN);
    }

    @Override
    public String buildWebAccessToken() {
        return buildToken(TokenTypeConst.WEB_ACCESS_TOKEN);
    }

    @Override
    public String buildWebRefreshToken() {
        return buildToken(TokenTypeConst.WEB_REFRESH_TOKEN);
    }

    @Override
    public String buildWechatToken() {
        return buildToken(TokenTypeConst.WECHAT_TOKEN);
    }

    @Override
    public <T> T getItem(String token, String itemKey, Class<?> collectionClass, Class<?>... elementClasses) {
        Map<String, Object> map = getTokenValue(token);
        Validator.assertNotEmpty(map, ErrCodeSys.PA_DATA_NOT_EXIST, "token内容");
        return JSONUtil.readJson(JSONUtil.toJSONString(map.get(itemKey)), collectionClass, elementClasses);
    }

    @Override
    public <T> T getItem(String itemKey, Class<?> collectionClass, Class<?>... elementClasses) {
        return getItem(getToken(), itemKey, collectionClass, elementClasses);
    }

    @Override
    public String getToken() {
        return getAndValidateToken();
    }

    @Override
    public void setToken(String token) {
        validateAndSetToken(token);
    }

    @Override
    public String getTokenType(String token) {
        return token.split(SEPARATION)[0];
    }

    @Override
    public Map<String, Object> getTokenValue(String token) {
        return redisService.get(getKey(token), Map.class);
    }

    @Override
    public Map<String, Object> getTokenValue() {
        return getTokenValue(getToken());
    }

    @Override
    public void setTokenValue(Map<String, Object> map) {
        setTokenValue(getToken(), map);
    }

    @Override
    public boolean isTokenExist(String token) {
        return redisService.hasKey(getKey(token));
    }

    @Override
    public boolean isTokenExist() {
        return isTokenExist(getToken());
    }

    @Override
    public void putItem(String token, String itemKey, Object value) {
        Map<String, Object> map = getTokenValue(token);
        if (MapUtils.isEmpty(map)) {
            map = new HashMap<>(16);
        }
        map.put(itemKey, value);
        setTokenValue(token, map);
    }

    @Override
    public void putItem(String itemKey, Object value) {
        putItem(getToken(), itemKey, value);
    }

    @Override
    public void removeItem(String token, String itemKey) {
        Map<String, Object> map = getTokenValue(token);
        Validator.assertNotEmpty(map, ErrCodeSys.PA_DATA_NOT_EXIST, "TOKEN");
        map.remove(itemKey);
        setTokenValue(token, map);
    }

    @Override
    public void removeItem(String itemKey) {
        String token = getAndValidateToken();
        removeItem(token, itemKey);
    }

    @Override
    public void removeToken(String token) {
        redisService.delete(getKey(token));
    }

    @Override
    public void removeToken() {
        removeToken(getKey(getToken()));
    }

    @Override
    public void setTokenValue(String token, Map<String, Object> map) {
        String tokenType = getTokenType(token);
        AccountParamConst expiredParam = getExpiredParamByTokenType(tokenType);
        int expired = frameCacheService.getParamInt(expiredParam);
        redisService.set(getKey(token), expired, map);
    }

    private String buildToken(TokenTypeConst tokenType) {
        String token = tokenType.toString() + SEPARATION + RandomUtil.getUUID();
        AccountParamConst expiredParam = getExpiredParamByTokenType(tokenType.toString());
        int expired = frameCacheService.getParamInt(expiredParam);
        Map<String, Object> tokenMap = new HashMap<>(16);
        tokenMap.put(tokenType.toString(), token);
        tokenMap.put(TIMESTAMP, new Date());
        redisService.set(getKey(token), expired, tokenMap);
        return token;
    }

    private String getAndValidateToken() {
        String token = TokenHolder.get();
        Validator.assertNotBlank(token, ErrCodeSys.SYS_SESSION_TIME_OUT);
        return token;
    }

    private AccountParamConst getExpiredParamByTokenType(String tokenType) {
        return AccountParamConst.valueOf(tokenType + EXPIRED_PARAM_SUFFIX);
    }

    private String getKey(String token) {
        return BASE_KEY + token;
    }

    private void validateAndSetToken(String token) {
        Validator.assertNotBlank(token, ErrCodeSys.PA_DATA_NOT_EXIST, "token");
        TokenHolder.set(token);
    }
}
