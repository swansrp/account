package com.srct.service.account.dao.common.mapper;

import com.srct.service.account.dao.common.entity.SmsSend;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SmsSendDao extends tk.mybatis.mapper.common.Mapper<SmsSend> {
    int updateBatch(List<SmsSend> list);

    int batchInsert(@Param("list") List<SmsSend> list);

    int insertOrUpdate(SmsSend record);

    int insertOrUpdateSelective(SmsSend record);
}