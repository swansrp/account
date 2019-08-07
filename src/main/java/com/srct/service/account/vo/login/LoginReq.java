/**
 * Title: LoginReq.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-28 15:18
 * @description Project Name: Grote
 * @Package: com.srct.service.account.vo
 */
package com.srct.service.account.vo.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginReq {

    @NotBlank(message = "用户标识不能为空")
    @ApiModelProperty(value = "用户标识")
    private String id;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message = "图形验证码不能为空")
    @ApiModelProperty(value = "图形验证码")
    private String graphCode;
}
