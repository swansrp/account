/**
 * Title: LoginService.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 17:56
 * @description Project Name: Grote
 * @Package: com.srct.service.account.service.common
 */
package com.srct.service.account.service;

import com.srct.service.account.dao.common.entity.User;
import com.srct.service.account.vo.login.LoginRes;
import com.srct.service.account.vo.platform.OpenPlatformAccessTokenResp;
import com.srct.service.account.vo.platform.OpenPlatformRegResp;

public interface LoginService {

    /**
     * 用户名密码登录.
     *
     * @param id       用户标识(用户名或者手机号或者邮箱)
     * @param password 密码
     * @return 登录返回信息
     */
    LoginRes login(String id, String password);

    /**
     * 短信验证码登录.
     *
     * @param phoneNumber 手机号码
     * @return 登录返回信息
     */
    LoginRes loginOrReg(String phoneNumber);


    /**
     * refreshToken登录.
     *
     * @param refreshToken 手机号码
     * @return 登录返回信息
     */
    LoginRes refreshLogin(String refreshToken);

    /**
     * 用户名密码注册.
     *
     * @param userId   用户名
     * @param password 密码
     * @return 登录返回信息
     */
    LoginRes register(String userId, String password);

    /**
     * 创建新用户
     *
     * @param phoneNumber
     * @param name
     * @return
     */
    User createUserWithName(String phoneNumber, String name);

    /**
     * 接入开放平台注册.
     *
     * @return 开放平台信息
     */
    OpenPlatformRegResp registerOpenPlatform();


    /**
     * 获取新的appSecret
     *
     * @param appKey
     * @return
     */
    OpenPlatformRegResp getOpenPlatformAppSecret(String appKey);

    /**
     * 开放平台获取accessToken
     *
     * @param appKey
     * @param appSecret
     * @return
     */
    OpenPlatformAccessTokenResp getOpenPlatformAccessToken(String appKey, String appSecret);

}
