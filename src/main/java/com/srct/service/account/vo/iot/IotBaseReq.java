package com.srct.service.account.vo.iot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Title: IotBaseReq
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/3 19:39
 * @description Project Name: Grote
 * @Package: com.srct.service.account.vo.iot
 */
@Data
public class IotBaseReq {
    @ApiModelProperty(value = "IOT设备唯一值")
    private String deviceId;
    @ApiModelProperty(value = "IOT设备token")
    private String token;
}
