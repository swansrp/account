package com.srct.service.account.dao.common.repository;

import com.srct.service.account.dao.common.entity.SmsTemplate;
import com.srct.service.account.dao.common.mapper.SmsTemplateDao;
import com.srct.service.frame.BaseRepository;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * Title: ${NAME}.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-8-7 11:36
 * @description Project Name: Grote
 * @Package: ${PACKAGE_NAME}
 */
@Service
public class SmsTemplateService extends BaseRepository<SmsTemplateDao, SmsTemplate> {

    @Resource
    private SmsTemplateDao smsTemplateDao;


    public int updateBatch(List<SmsTemplate> list) {
        return smsTemplateDao.updateBatch(list);
    }


    public int batchInsert(List<SmsTemplate> list) {
        return smsTemplateDao.batchInsert(list);
    }


    public int insertOrUpdate(SmsTemplate record) {
        return smsTemplateDao.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(SmsTemplate record) {
        return smsTemplateDao.insertOrUpdateSelective(record);
    }

    @Override
    protected SmsTemplateDao getDao() {
        return smsTemplateDao;
    }

    public SmsTemplate selectOneBySendSmsType(String sendSmsType) {
        Example example = new Example(SmsTemplate.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("sendSmsType", sendSmsType);
        return smsTemplateDao.selectOneByExample(example);
    }
}
