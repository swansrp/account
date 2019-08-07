/**
 * Title: UserPermitDao.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-31 16:23
 * @description Project Name: Grote
 * @Package: com.srct.service.account.dao.mapper
 */
package com.srct.service.account.dao.mapper;

import com.srct.service.account.dao.entity.Permit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserPermitDao {

    /**
     * Select permit by user id and Client type.
     *
     * @param userId     the user id
     * @param clientType the client type
     * @return the list
     */
    List<Permit> selectPermitByUserIdAndClientType(@Param("userId") String userId, @Param("clientType") String clientType);

}
