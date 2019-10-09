package com.srct.service.account.dao.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;

@ApiModel(value="com.srct.service.account.dao.common.entity.SmsSend")
@Table(name = "sa_sms_send")
public class SmsSend {
    /**
     * 发送流水号
     */
    @Id
    @Column(name = "send_id")
    @ApiModelProperty(value="发送流水号")
    private String sendId;

    /**
     * 手机号码
     */
    @Column(name = "mobile")
    @ApiModelProperty(value="手机号码")
    private String mobile;

    /**
     * 发送参数表
     */
    @Column(name = "send_msg")
    @ApiModelProperty(value="发送参数表")
    private String sendMsg;

    /**
     * 发送状态
     */
    @Column(name = "send_status")
    @ApiModelProperty(value="发送状态")
    private String sendStatus;

    /**
     * 发送时间
     */
    @Column(name = "send_time")
    @ApiModelProperty(value="发送时间")
    private Date sendTime;

    /**
     * 发送结果
     */
    @Column(name = "send_result")
    @ApiModelProperty(value="发送结果")
    private String sendResult;

    /**
     * 发送类型
     */
    @Column(name = "send_type")
    @ApiModelProperty(value="发送类型")
    private String sendType;

    /**
     * 发送模板
     */
    @Column(name = "template_id")
    @ApiModelProperty(value="发送模板")
    private String templateId;

    /**
     * 获取发送流水号
     *
     * @return send_id - 发送流水号
     */
    public String getSendId() {
        return sendId;
    }

    /**
     * 设置发送流水号
     *
     * @param sendId 发送流水号
     */
    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    /**
     * 获取手机号码
     *
     * @return mobile - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取发送参数表
     *
     * @return send_msg - 发送参数表
     */
    public String getSendMsg() {
        return sendMsg;
    }

    /**
     * 设置发送参数表
     *
     * @param sendMsg 发送参数表
     */
    public void setSendMsg(String sendMsg) {
        this.sendMsg = sendMsg;
    }

    /**
     * 获取发送状态
     *
     * @return send_status - 发送状态
     */
    public String getSendStatus() {
        return sendStatus;
    }

    /**
     * 设置发送状态
     *
     * @param sendStatus 发送状态
     */
    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    /**
     * 获取发送时间
     *
     * @return send_time - 发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置发送时间
     *
     * @param sendTime 发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取发送结果
     *
     * @return send_result - 发送结果
     */
    public String getSendResult() {
        return sendResult;
    }

    /**
     * 设置发送结果
     *
     * @param sendResult 发送结果
     */
    public void setSendResult(String sendResult) {
        this.sendResult = sendResult;
    }

    /**
     * 获取发送类型
     *
     * @return send_type - 发送类型
     */
    public String getSendType() {
        return sendType;
    }

    /**
     * 设置发送类型
     *
     * @param sendType 发送类型
     */
    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    /**
     * 获取发送模板
     *
     * @return template_id - 发送模板
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * 设置发送模板
     *
     * @param templateId 发送模板
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}