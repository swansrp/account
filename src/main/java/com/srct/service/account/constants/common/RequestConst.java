/**
 * Title: RequestConst.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-28 8:03
 * @description Project Name: Grote
 * @Package: com.srct.service.account.constants
 */
package com.srct.service.account.constants.common;

public class RequestConst {
    public static final String TOKEN = "x-access-token";
    public static final String API_VERSION = "api-version";
    public static final String USER_ID = "user-id";
    public static final String AUTHORIZATION = "authorization";
    public static final String CLIENT_TYPE = "client-type";

    public static final String CONTENT_TYPE = "content-type";

    public static final String SERVICE_API = "serviceApi";
    public static final String OBJECT = "object";

    public static String getAllHeader() {
        return TOKEN + ", " + API_VERSION + ", " + USER_ID + ", " + AUTHORIZATION + ", " + CLIENT_TYPE + ", "
                + CONTENT_TYPE;
    }
}
