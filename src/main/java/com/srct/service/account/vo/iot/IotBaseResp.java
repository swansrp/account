package com.srct.service.account.vo.iot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Title: IotBaseResp
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/3 20:04
 * @description Project Name: Grote
 * @Package: com.srct.service.account.vo.iot
 */
@Data
public class IotBaseResp {
    @ApiModelProperty(value = "token更新状态开关 0正常 1需要更新")
    private String tokenUpdateSwitch;
}
