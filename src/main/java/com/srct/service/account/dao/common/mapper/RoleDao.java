package com.srct.service.account.dao.common.mapper;

import com.srct.service.account.dao.common.entity.Role;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleDao extends tk.mybatis.mapper.common.Mapper<Role> {
    int updateBatch(List<Role> list);

    int batchInsert(@Param("list") List<Role> list);

    int insertOrUpdate(Role record);

    int insertOrUpdateSelective(Role record);
}