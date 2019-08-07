package com.srct.service.account.dao.mapper;

import com.srct.service.account.dao.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao extends tk.mybatis.mapper.common.Mapper<User> {
    int updateBatch(List<User> list);

    int batchInsert(@Param("list") List<User> list);

    int insertOrUpdate(User record);

    int insertOrUpdateSelective(User record);
}
