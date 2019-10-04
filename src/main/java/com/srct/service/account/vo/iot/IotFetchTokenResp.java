package com.srct.service.account.vo.iot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Title: IotFetchTokenResp
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/3 23:13
 * @description Project Name: Grote
 * @Package: com.srct.service.account.vo.iot
 */
@Data
public class IotFetchTokenResp extends IotBaseResp {
    @ApiModelProperty(value = "返回token值")
    private String authToken;
}
