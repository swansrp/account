package com.srct.service.account.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@ApiModel(value = "com.srct.service.account.dao.entity.Permit")
@Data
@Table(name = "ac_permit")
public class Permit {
    /**
     * 权限ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "权限ID")
    private Integer id;

    /**
     * 父权限ID
     */
    @Column(name = "parent_permit_id")
    @ApiModelProperty(value = "父权限ID")
    private Integer parentPermitId;

    /**
     * 权限类型 MENU|BUTTON
     */
    @Column(name = "permit_type")
    @ApiModelProperty(value = "权限类型 MENU|BUTTON")
    private String permitType;

    /**
     * 权限名称
     */
    @Column(name = "`name`")
    @ApiModelProperty(value = "权限名称")
    private String name;

    /**
     * 权限KEY 下划线式驼峰命名
     */
    @Column(name = "`key`")
    @ApiModelProperty(value = "权限KEY 下划线式驼峰命名")
    private String key;

    /**
     * 客户端类型
     */
    @Column(name = "client_type")
    @ApiModelProperty(value = "客户端类型")
    private String clientType;

    /**
     * URL
     */
    @Column(name = "url")
    @ApiModelProperty(value = "URL")
    private String url;

    /**
     * 显示名称
     */
    @Column(name = "show_name")
    @ApiModelProperty(value = "显示名称")
    private String showName;

    /**
     * 显示顺序
     */
    @Column(name = "show_order")
    @ApiModelProperty(value = "显示顺序")
    private Integer showOrder;

    /**
     * 状态 0 禁用 1 可用
     */
    @Column(name = "`status`")
    @ApiModelProperty(value = "状态 0 禁用 1 可用")
    private String status;

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
     * 图标
     */
    @Column(name = "icon")
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 有效性
     */
    @Column(name = "`valid`")
    @ApiModelProperty(value = "有效性")
    private Byte valid;
}