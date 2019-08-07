/**
 * Title: MsgLoginReq.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-31 21:46
 * @description Project Name: Grote
 * @Package: com.srct.service.account.vo
 */
package com.srct.service.account.vo.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MsgLoginReq {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String phoneNumber;

    @NotBlank(message = "短信验证码不能为空")
    @ApiModelProperty(value = "短信验证码")
    private String loginMsgCode;
}
