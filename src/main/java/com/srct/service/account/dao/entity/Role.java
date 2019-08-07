package com.srct.service.account.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value = "com.srct.service.account.dao.entity.Role")
@Data
@Table(name = "ac_role")
public class Role {
    /**
     * 角色ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "角色ID")
    private Integer id;

    /**
     * 父角色ID
     */
    @Column(name = "parent_role_id")
    @ApiModelProperty(value = "父角色ID")
    private Integer parentRoleId;

    /**
     * 角色名称
     */
    @Column(name = "`name`")
    @ApiModelProperty(value = "角色名称")
    private String name;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "last_update_time")
    @ApiModelProperty(value = "修改时间")
    private Date lastUpdateTime;

    /**
     * 有效性
     */
    @Column(name = "`valid`")
    @ApiModelProperty(value = "有效性")
    private Byte valid;
}