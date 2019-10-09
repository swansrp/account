package com.srct.service.account.dao.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value="com.srct.service.account.dao.common.entity.RolePermit")
@Data
@Table(name = "ac_role_permit")
public class RolePermit {
    /**
     * 权限ID
     */
    @Id
    @Column(name = "permit_id")
    @ApiModelProperty(value="权限ID")
    private Integer permitId;

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
