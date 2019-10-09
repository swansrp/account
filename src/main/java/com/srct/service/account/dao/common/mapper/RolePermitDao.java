package com.srct.service.account.dao.common.mapper;

import com.srct.service.account.dao.common.entity.RolePermit;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RolePermitDao extends tk.mybatis.mapper.common.Mapper<RolePermit> {
    int updateBatch(List<RolePermit> list);

    int batchInsert(@Param("list") List<RolePermit> list);

    int insertOrUpdate(RolePermit record);

    int insertOrUpdateSelective(RolePermit record);
}
