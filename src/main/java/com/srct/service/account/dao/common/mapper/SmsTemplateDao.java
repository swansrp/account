package com.srct.service.account.dao.common.mapper;

import com.srct.service.account.dao.common.entity.SmsTemplate;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SmsTemplateDao extends tk.mybatis.mapper.common.Mapper<SmsTemplate> {
    int updateBatch(List<SmsTemplate> list);

    int batchInsert(@Param("list") List<SmsTemplate> list);

    int insertOrUpdate(SmsTemplate record);

    int insertOrUpdateSelective(SmsTemplate record);
}