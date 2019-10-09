/**
 * Title: AliSmsServiceImpl.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-8-7 11:22
 * @description Project Name: Grote
 * @Package: com.srct.service.account.service.impl
 */
package com.srct.service.account.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.srct.service.account.constants.ali.AliParamConst;
import com.srct.service.account.constants.sms.SmsParamConst;
import com.srct.service.account.constants.sms.SmsSeqConst;
import com.srct.service.account.dao.common.entity.SmsSend;
import com.srct.service.account.dao.common.entity.SmsTemplate;
import com.srct.service.account.dao.common.repository.SmsSendService;
import com.srct.service.account.dao.common.repository.SmsTemplateService;
import com.srct.service.bo.sms.SendSmsReq;
import com.srct.service.bo.sms.SendSmsRes;
import com.srct.service.cache.constant.MsgVerificationType;
import com.srct.service.constant.ParamFrame;
import com.srct.service.dao.repository.CommonSequenceService;
import com.srct.service.service.cache.FrameCacheService;
import com.srct.service.service.sms.SmsService;
import com.srct.service.utils.JSONUtil;
import com.srct.service.utils.log.Log;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AliSmsServiceImpl implements SmsService {

    private static final String ALI_DOMAIN_URL = "dysmsapi.aliyuncs.com";
    private static final String ALI_SMS_VERSION = "2017-05-25";
    private static final String ALI_SEND_SMS_ACTION = "SendSms";

    @Resource
    private FrameCacheService frameCacheService;
    @Resource
    private SmsSendService smsSendService;
    @Resource
    private SmsTemplateService smsTemplateService;
    @Resource
    private CommonSequenceService commonSequenceService;

    @Override
    public void sendSms(SendSmsReq sendSmsReq) {
        String regionId = frameCacheService.getParamValueAvail(SmsParamConst.SMS_REGION_ID);
        String singName = frameCacheService.getParamValueAvail(SmsParamConst.SMS_SIGN);
        String accessKeyId = frameCacheService.getParamValueAvail(AliParamConst.ALI_ACCESS_KEY);
        String accessSecret = frameCacheService.getParamValueAvail(AliParamConst.ALI_ACCESS_SECRET);
        String sendSmsType = frameCacheService
                .getDictItemName(MsgVerificationType.MSG_VERIFICATION_TYPE, sendSmsReq.getSendSmsType().name());
        SmsTemplate smsTemplate = smsTemplateService.selectOneBySendSmsType(sendSmsType);
        String templateCode = smsTemplate.getTemplateId();
        String templateParam = JSONUtil.toJSONString(sendSmsReq.getParamMap(), false, false, true);
        String phoneNumber = sendSmsReq.getPhoneNumbers();
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(ALI_DOMAIN_URL);
        request.setVersion(ALI_SMS_VERSION);
        request.setAction(ALI_SEND_SMS_ACTION);
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", singName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        SmsSend smsSend = buildSmsSend(phoneNumber, templateCode, templateParam, sendSmsType);
        if (frameCacheService.getParamSwitch(ParamFrame.TEST_MODE_GRAPH_VALIDATE_SWITCH)){
            saveSendSms(smsSend);
        } else{
            try {
                CommonResponse response = client.getCommonResponse(request);
                saveSendSms(smsSend, response);
            } catch (ServerException e) {
                Log.e(e);
            } catch (ClientException e) {
                Log.e(e);
            }
        }
    }

    private void saveSendSms(SmsSend smsSend) {
        smsSend.setSendStatus("本地测试");
        smsSend.setSendResult("发送成功");
        smsSend.setSendId(commonSequenceService.getSeq(SmsSeqConst.SEND_SMS_SEQ.name()));
        smsSendService.insertOrUpdateSelective(smsSend);
    }

    private void saveSendSms(SmsSend smsSend, CommonResponse response) {
        SendSmsRes res = JSONUtil.readJson(response.getData(), SendSmsRes.class);
        smsSend.setSendStatus(res.getCode());
        smsSend.setSendResult(res.getMessage());
        smsSend.setSendId(commonSequenceService.getSeq(SmsSeqConst.SEND_SMS_SEQ.name()));
        smsSendService.insertOrUpdateSelective(smsSend);
    }

    private SmsSend buildSmsSend(String phoneNumber, String templateCode, String templateParam, String sendType) {
        SmsSend smsSend = new SmsSend();
        smsSend.setMobile(phoneNumber);
        smsSend.setSendMsg(templateParam);
        smsSend.setTemplateId(templateCode);
        smsSend.setSendType(sendType);
        smsSend.setSendTime(new Date());
        return smsSend;
    }

}
