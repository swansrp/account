package com.srct.service.account.utils;

import com.srct.service.account.constants.common.RequestConst;
import com.srct.service.utils.HttpUtil;
import com.srct.service.utils.security.Base64Util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Title: AuthStringUtil
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/9 15:07
 * @description Project Name: Grote
 * @Package: com.srct.service.account.utils
 */
public class AuthStringUtil {
    public static Map<String, String> decodeAuthorization(HttpServletRequest request) {
        Map<String, String> authMap = new HashMap<>(2);
        try {
            Map<String, String> headersInfoMap = HttpUtil.getHeadersInfoMap(request);
            String authorization = headersInfoMap.get(RequestConst.AUTHORIZATION);
            String keyPair = Base64Util.decode(authorization.split(" ")[1]);
            String[] strArray = keyPair.split(":");
            authMap.put(RequestConst.APP_KEY, strArray[0]);
            authMap.put(RequestConst.APP_SECRET, strArray[1]);
        } catch (Exception e) {

        }
        return authMap;
    }
}
