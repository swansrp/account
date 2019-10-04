/**
 * Title: AccountParamConst.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-27 23:17
 * @description Project Name: Grote
 * @Package: com.srct.service.account.constants
 */
package com.srct.service.account.constants.account;

import com.srct.service.constant.Param;

public enum AccountParamConst implements Param {

    // APP access_token有效期
    APP_ACCESS_TOKEN_EXPIRED,
    // APP refresh_token有效期
    APP_REFRESH_TOKEN_EXPIRED,
    // 网页端access_token有效期
    WEB_ACCESS_TOKEN_EXPIRED,
    // 网页端refresh_token有效期
    WEB_REFRESH_TOKEN_EXPIRED,
    // 微信小程序 access_token有效期
    WECHAT_TOKEN_EXPIRED,
    // IOT token有效期
    IOT_TOKEN_EXPIRED,
    // IOT token期望更新周期
    IOT_TOKEN_UPDATE_EXPIRED,


    // 密码输入错误锁定次数
    ACCOUNT_LOCK_MISTAKE_NUMBER,
    // 新注册用户默认角色ID
    ACCOUNT_DEFAULT_ROLE_ID
}
