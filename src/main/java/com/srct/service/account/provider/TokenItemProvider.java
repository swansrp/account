/**
 * Title: TokenItemProvider.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-28 11:03
 * @description Project Name: Grote
 * @Package: com.srct.service.account.provider
 */
package com.srct.service.account.provider;

import com.srct.service.account.constants.token.TokenItemConst;
import com.srct.service.service.cache.FrameCacheService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TokenItemProvider {

    @Resource
    private FrameCacheService frameCacheService;

    public String getOperator() {
        return frameCacheService.getDictItemName(TokenItemConst.TOKEN_ITEM, TokenItemConst.OPERATOR.name());
    }

    public String getPemritTree() {
        return frameCacheService.getDictItemName(TokenItemConst.TOKEN_ITEM, TokenItemConst.PERMIT_TREE.name());
    }

    public String getLoginMsgCode() {
        return frameCacheService.getDictItemName(TokenItemConst.TOKEN_ITEM, TokenItemConst.LOGIN_MSG_CODE.name());
    }

    public String getFindPasswordMsgCode() {
        return frameCacheService
                .getDictItemName(TokenItemConst.TOKEN_ITEM, TokenItemConst.FIND_PASSWORD_MSG_CODE.name());
    }


}
