/**
 * Title: TokenTypeUtil.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-8-3 16:00
 * @description Project Name: Grote
 * @Package: com.srct.service.account.utils
 */
package com.srct.service.account.utils;

import com.srct.service.account.constants.common.ClientTypeConst;
import com.srct.service.account.constants.token.TokenTypeConst;

public class TokenTypeUtil {

    public static String getTokenTypeClientType(String clientType) {
        switch (ClientTypeConst.valueOf(clientType)) {
            case WEB:
                return TokenTypeConst.WEB_ACCESS_TOKEN.name();
            case APP:
                return TokenTypeConst.APP_ACCESS_TOKEN.name();
            default:
                return null;
        }
    }
}
