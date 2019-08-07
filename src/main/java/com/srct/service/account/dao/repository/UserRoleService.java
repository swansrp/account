package com.srct.service.account.dao.repository;

import com.srct.service.account.dao.entity.UserRole;
import com.srct.service.account.dao.mapper.UserRoleDao;
import com.srct.service.frame.BaseRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Title: ${NAME}.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-26 17:55
 * @description Project Name: Grote
 * @Package: ${PACKAGE_NAME}
 */
@Service
public class UserRoleService extends BaseRepository<UserRoleDao, UserRole> {

    @Resource
    private UserRoleDao userRoleDao;


    public int updateBatch(List<UserRole> list) {
        return userRoleDao.updateBatch(list);
    }


    public int batchInsert(List<UserRole> list) {
        return userRoleDao.batchInsert(list);
    }


    public int insertOrUpdate(UserRole record) {
        return userRoleDao.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(UserRole record) {
        return userRoleDao.insertOrUpdateSelective(record);
    }

    @Override
    protected UserRoleDao getDao() {
        return userRoleDao;
    }
}
