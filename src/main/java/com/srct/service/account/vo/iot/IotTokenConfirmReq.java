package com.srct.service.account.vo.iot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Title: IotTokenConfirmReq
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/3 20:30
 * @description Project Name: Grote
 * @Package: com.srct.service.account.vo.iot
 */
@Data
public class IotTokenConfirmReq extends IotBaseReq {
    @ApiModelProperty(value = "需要确认使能token")
    private String tokenConfirmed;
}
