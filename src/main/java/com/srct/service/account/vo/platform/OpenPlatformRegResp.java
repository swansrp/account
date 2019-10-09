package com.srct.service.account.vo.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Title: OpenPlatformRegResp
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/9 10:26
 * @description Project Name: Grote
 * @Package: com.srct.service.account.vo.platform
 */
@Data
public class OpenPlatformRegResp {
    @ApiModelProperty("接入id")
    private String appKey;
    @ApiModelProperty("接入秘钥")
    private String appSecret;
    @ApiModelProperty("头部鉴权字符串")
    private String authString;
}
