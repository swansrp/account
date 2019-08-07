/**
 * Title: PermitBO.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-31 16:11
 * @description Project Name: Grote
 * @Package: com.srct.service.account.bo
 */
package com.srct.service.account.bo.permit;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PermitBO {


    /**
     * 权限ID
     */
    @ApiModelProperty(value = "权限ID")
    private Integer id;
    /**
     * 父权限ID
     */
    @ApiModelProperty(value = "父权限ID")
    private Integer parentPermitId;

    /**
     * 权限类型 MENU|BUTTON
     */
    @ApiModelProperty(value = "权限类型 MENU|BUTTON")
    private String permitType;

    /**
     * 权限名称
     */
    @ApiModelProperty(value = "权限名称")
    private String name;

    /**
     * 权限KEY 下划线式驼峰命名
     */
    @ApiModelProperty(value = "权限KEY 下划线式驼峰命名")
    private String key;

    /**
     * 客户端权限
     */
    @ApiModelProperty(value = "客户端类型")
    private String clientType;

    /**
     * URL
     */
    @ApiModelProperty(value = "URL")
    private String url;

    /**
     * 显示名称
     */
    @ApiModelProperty(value = "显示名称")
    private String showName;


    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

}
