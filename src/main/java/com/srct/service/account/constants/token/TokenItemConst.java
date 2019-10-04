/**
 * Title: TokenItemConst.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-28 10:52
 * @description Project Name: Grote
 * @Package: com.srct.service.account.constants
 */
package com.srct.service.account.constants.token;

import com.srct.service.constant.Dict;

public enum TokenItemConst implements Dict {
    // Token 内容
    TOKEN_ITEM,
    // 用户名
    OPERATOR,
    // 权限树,
    PERMIT_TREE,
    // 登录短信验证码,
    LOGIN_MSG_CODE,
    // 找回密码短信验证码,
    FIND_PASSWORD_MSG_CODE,
    // IOT token刷新时间戳
    IOT_TOKEN_NEED_UPDATE_TIMESTAMP,
    // IOT deviceId
    IOT_DEVICE_ID,
    // IOT 待更新的token
    IOT_UPDATE_TOKEN,
}
