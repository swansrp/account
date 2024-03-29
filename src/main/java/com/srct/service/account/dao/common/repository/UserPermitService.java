/**
 * Title: UserPermitService.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-31 16:46
 * @description Project Name: Grote
 * @Package: com.srct.service.account.dao.common.repository
 */
package com.srct.service.account.dao.common.repository;

import com.srct.service.account.dao.common.entity.Permit;
import com.srct.service.account.dao.common.mapper.UserPermitDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserPermitService {
    @Resource
    private UserPermitDao userPermitDao;

    public List<Permit> getPermitListByUserIdAndClientType(String userId, String clientType) {
        return userPermitDao.selectPermitByUserIdAndClientType(userId, clientType);
    }
}
