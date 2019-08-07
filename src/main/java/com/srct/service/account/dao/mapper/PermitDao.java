package com.srct.service.account.dao.mapper;

import com.srct.service.account.dao.entity.Permit;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PermitDao extends tk.mybatis.mapper.common.Mapper<Permit> {
    int updateBatch(List<Permit> list);

    int batchInsert(@Param("list") List<Permit> list);

    int insertOrUpdate(Permit record);

    int insertOrUpdateSelective(Permit record);
}