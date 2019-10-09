package com.srct.service.account.dao.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

/**
 * Title: User
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/3 19:30
 * @description Project Name: Grote
 * @Package: com.srct.service.account.dao.common.entity
 */
@ApiModel(value = "com.srct.service.account.dao.common.entity.User")
@Data
@Table(name = "ac_user")
public class User {
    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 系统唯一标识
     */
    @Column(name = "guid")
    @ApiModelProperty(value = "系统唯一标识")
    private String guid;

    /**
     * 用户流水号
     */
    @Column(name = "customer_no")
    @ApiModelProperty(value = "用户流水号")
    private String customerNo;

    /**
     * 用户登录名
     */
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户登录名")
    private String userId;

    /**
     * 密码
     */
    @Column(name = "`password`")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 用户名称
     */
    @Column(name = "`name`")
    @ApiModelProperty(value = "用户名称")
    private String name;

    /**
     * 电话号码
     */
    @Column(name = "phone")
    @ApiModelProperty(value = "电话号码")
    private String phone;

    /**
     * 邮箱号码
     */
    @Column(name = "email")
    @ApiModelProperty(value = "邮箱号码")
    private String email;

    /**
     * 微信openId
     */
    @Column(name = "wechat_id")
    @ApiModelProperty(value = "微信openId")
    private String wechatId;

    /**
     * 身份证号码
     */
    @Column(name = "id_card_num")
    @ApiModelProperty(value = "身份证号码")
    private String idCardNum;

    /**
     * openAPI key
     */
    @Column(name = "app_key")
    @ApiModelProperty(value = "openAPI key")
    private String appKey;

    /**
     * openAPI secret
     */
    @Column(name = "app_secret")
    @ApiModelProperty(value = "openAPI secret")
    private String appSecret;

    /**
     * 备注
     */
    @Column(name = "`comment`")
    @ApiModelProperty(value = "备注")
    private String comment;

    /**
     * 连续输错密码次数，输入正确密码后归零
     */
    @Column(name = "password_error_times")
    @ApiModelProperty(value = "连续输错密码次数，输入正确密码后归零")
    private Integer passwordErrorTimes;

    /**
     * 上次输错密码时间
     */
    @Column(name = "password_error_last_time")
    @ApiModelProperty(value = "上次输错密码时间")
    private Date passwordErrorLastTime;

    /**
     * 上次密码修改时间
     */
    @Column(name = "password_update_last_time")
    @ApiModelProperty(value = "上次密码修改时间")
    private Date passwordUpdateLastTime;

    /**
     * 上次登录时间
     */
    @Column(name = "last_login_time")
    @ApiModelProperty(value = "上次登录时间")
    private Date lastLoginTime;

    /**
     * 状态 0 禁用 1 可用
     */
    @Column(name = "`status`")
    @ApiModelProperty(value = "状态 0 禁用 1 可用")
    private String status;

    /**
     * 所属单位
     */
    @Column(name = "company")
    @ApiModelProperty(value = "所属单位")
    private Integer company;

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