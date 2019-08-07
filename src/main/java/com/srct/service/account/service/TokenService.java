/**
 * Title: TokenService.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 18:04
 * @description Project Name: Grote
 * @Package: com.srct.service.account.service
 */
package com.srct.service.account.service;

import java.util.Map;

public interface TokenService {

    /**
     * 获取APP access holder
     *
     * @return APP access holder
     */
    String buildAppAccessToken();

    /**
     * 获取APP refresh holder
     *
     * @return APP refresh holder
     */
    String buildAppRefreshToken();

    /**
     * 获取网页 web access holder
     *
     * @return web access holder
     */
    String buildWebAccessToken();

    /**
     * 获取网页 web refresh holder
     *
     * @return web refresh holder
     */
    String buildWebRefreshToken();

    /**
     * 获取小程序token
     *
     * @return 小程序Token string
     */
    String buildWechatToken();

    /**
     * 获取token map中itemKey内容
     *
     * @param <T>             the type parameter
     * @param token           holder
     * @param itemKey         holder,map中的item key值
     * @param collectionClass holder,map中的item value值类型
     * @param elementClasses  holder,map中的item value值类型
     * @return holder, map中的item itemKey对应内容value
     */
    <T> T getItem(String token, String itemKey, Class<?> collectionClass, Class<?>... elementClasses);

    /**
     * 获取token map中itemKey内容
     *
     * @param <T>             the type parameter
     * @param itemKey         holder,map中的item key值
     * @param collectionClass holder,map中的item value值类型
     * @param elementClasses  holder,map中的item value值类型
     * @return holder, map中的item itemKey对应内容value
     */
    <T> T getItem(String itemKey, Class<?> collectionClass, Class<?>... elementClasses);

    /**
     * 获取当前线程token.
     *
     * @return the holder
     */
    String getToken();

    /**
     * 设置当前线程token.
     *
     * @return the holder
     */
    void setToken(String token);

    /**
     * 获取token中map内容
     *
     * @param token holder
     * @return holder, 中的map内容
     */
    Map<String, Object> getTokenValue(String token);

    /**
     * 获取token中map内容
     *
     * @return holder, 中的map内容
     */
    Map<String, Object> getTokenValue();

    /**
     * 将map存入token
     *
     * @param map 替换token中map内容
     */
    void setTokenValue(Map<String, Object> map);

    /**
     * 查看 TOKEN 是否存在
     *
     * @param token the holder
     * @return if holder existed
     */
    boolean isTokenExist(String token);

    /**
     * 查看 TOKEN 是否存在
     *
     * @return if holder existed
     */
    boolean isTokenExist();

    /**
     * 将itemKey - value 存入token map中
     *
     * @param token   holder
     * @param itemKey holder,map中的item key值
     * @param value   holder,map中的item itemKey对应内容value
     */
    void putItem(String token, String itemKey, Object value);

    /**
     * 将itemKey - value 存入token map中
     *
     * @param itemKey holder,map中的item key值
     * @param value   holder,map中的item itemKey对应内容value
     */
    void putItem(String itemKey, Object value);

    /**
     * 删除token map中itemKey内容
     *
     * @param token   holder
     * @param itemKey holder,map中的item key值
     */
    void removeItem(String token, String itemKey);

    /**
     * 删除token map中itemKey内容
     *
     * @param itemKey holder,map中的item key值
     */
    void removeItem(String itemKey);

    /**
     * 删除token
     *
     * @param token holder
     */
    void removeToken(String token);

    /**
     * 删除token
     */
    void removeToken();

    /**
     * 将map存入token
     *
     * @param token
     * @param map   替换token中map内容
     */
    void setTokenValue(String token, Map<String, Object> map);

    /**
     * 获取token类型
     *
     * @param token
     * @return token类型
     */
    String getTokenType(String token);

}
