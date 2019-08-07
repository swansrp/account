package com.srct.service.account.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="com.srct.service.account.dao.entity.UserRole")
@Data
@Table(name = "ac_user_role")
public class UserRole {
    /**
     * 用户ID
     */
    @Id
    @Column(name = "user_id")
    @ApiModelProperty(value="用户ID")
    private Integer userId;

    /**
     * 角色ID
     */
    @Id
    @Column(name = "role_id")
    @ApiModelProperty(value="角色ID")
    private Integer roleId;

    /**
     * 有效性
     */
    @Column(name = "`valid`")
    @ApiModelProperty(value="有效性")
    private Byte valid;
}
