package com.srct.service.account.dao.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;

@ApiModel(value="com.srct.service.account.dao.entity.SmsTemplate")
@Table(name = "sa_sms_template")
public class SmsTemplate {
    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value="id")
    private Integer id;

    /**
     * 发送短信类型
     */
    @Column(name = "send_sms_type")
    @ApiModelProperty(value="发送短信类型")
    private String sendSmsType;

    /**
     * 云平台短信模板
     */
    @Column(name = "template_id")
    @ApiModelProperty(value="云平台短信模板")
    private String templateId;

    /**
     * 短信模板参数个数
     */
    @Column(name = "paramer_number")
    @ApiModelProperty(value="短信模板参数个数")
    private Integer paramerNumber;

    /**
     * 短信模板内容
     */
    @Column(name = "body")
    @ApiModelProperty(value="短信模板内容")
    private String body;

    /**
     * 作者
     */
    @Column(name = "author")
    @ApiModelProperty(value="作者")
    private String author;

    /**
     * 审批时间
     */
    @Column(name = "comfirm_at")
    @ApiModelProperty(value="审批时间")
    private Date comfirmAt;

    /**
     * 0 拒绝 1 同意
     */
    @Column(name = "comfirm_status")
    @ApiModelProperty(value="0 拒绝 1 同意")
    private String comfirmStatus;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取发送短信类型
     *
     * @return send_sms_type - 发送短信类型
     */
    public String getSendSmsType() {
        return sendSmsType;
    }

    /**
     * 设置发送短信类型
     *
     * @param sendSmsType 发送短信类型
     */
    public void setSendSmsType(String sendSmsType) {
        this.sendSmsType = sendSmsType;
    }

    /**
     * 获取云平台短信模板
     *
     * @return template_id - 云平台短信模板
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * 设置云平台短信模板
     *
     * @param templateId 云平台短信模板
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * 获取短信模板参数个数
     *
     * @return paramer_number - 短信模板参数个数
     */
    public Integer getParamerNumber() {
        return paramerNumber;
    }

    /**
     * 设置短信模板参数个数
     *
     * @param paramerNumber 短信模板参数个数
     */
    public void setParamerNumber(Integer paramerNumber) {
        this.paramerNumber = paramerNumber;
    }

    /**
     * 获取短信模板内容
     *
     * @return body - 短信模板内容
     */
    public String getBody() {
        return body;
    }

    /**
     * 设置短信模板内容
     *
     * @param body 短信模板内容
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 获取作者
     *
     * @return author - 作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取审批时间
     *
     * @return comfirm_at - 审批时间
     */
    public Date getComfirmAt() {
        return comfirmAt;
    }

    /**
     * 设置审批时间
     *
     * @param comfirmAt 审批时间
     */
    public void setComfirmAt(Date comfirmAt) {
        this.comfirmAt = comfirmAt;
    }

    /**
     * 获取0 拒绝 1 同意
     *
     * @return comfirm_status - 0 拒绝 1 同意
     */
    public String getComfirmStatus() {
        return comfirmStatus;
    }

    /**
     * 设置0 拒绝 1 同意
     *
     * @param comfirmStatus 0 拒绝 1 同意
     */
    public void setComfirmStatus(String comfirmStatus) {
        this.comfirmStatus = comfirmStatus;
    }
}