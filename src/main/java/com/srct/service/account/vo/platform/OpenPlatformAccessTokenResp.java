package com.srct.service.account.vo.platform;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Title: OpenPlatformAccessTokenResp
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/9 10:50
 * @description Project Name: Grote
 * @Package: com.srct.service.account.vo.platform
 */
@Data
public class OpenPlatformAccessTokenResp {
    @ApiModelProperty("接入appKey")
    private String appKey;
    @ApiModelProperty("接入token")
    private String accessToken;
}
