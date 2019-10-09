package com.srct.service.account.dao.common.mapper;

import com.srct.service.account.dao.common.entity.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Title: UserDao
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/3 19:30
 * @description Project Name: Grote
 * @Package: com.srct.service.account.dao.common.mapper
 */
@Mapper
public interface UserDao extends tk.mybatis.mapper.common.Mapper<User> {
    int updateBatch(List<User> list);

    int batchInsert(@Param("list") List<User> list);

    int insertOrUpdate(User record);

    int insertOrUpdateSelective(User record);
}