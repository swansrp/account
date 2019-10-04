/**
 * Title: TokenTypeConst.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-27 21:10
 * @description Project Name: Grote
 * @Package: com.srct.service.account.constants
 */
package com.srct.service.account.constants.token;

import com.srct.service.constant.Dict;

public enum TokenTypeConst implements Dict {
    // TokenType
    TOKEN_TYPE,
    // 微信接入token
    WECHAT_TOKEN,
    // 网页接入token
    WEB_ACCESS_TOKEN,
    // 网页刷新token
    WEB_REFRESH_TOKEN,
    // 应用接入token
    APP_ACCESS_TOKEN,
    // 应用刷新token
    APP_REFRESH_TOKEN,
    // iot token
    IOT_TOKEN,


}
