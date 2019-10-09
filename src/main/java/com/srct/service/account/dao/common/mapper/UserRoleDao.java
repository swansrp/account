package com.srct.service.account.dao.common.mapper;

import com.srct.service.account.dao.common.entity.UserRole;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleDao extends tk.mybatis.mapper.common.Mapper<UserRole> {
    int updateBatch(List<UserRole> list);

    int batchInsert(@Param("list") List<UserRole> list);

    int insertOrUpdate(UserRole record);

    int insertOrUpdateSelective(UserRole record);
}
