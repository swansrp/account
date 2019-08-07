package com.srct.service.account.dao.repository;

import com.srct.service.account.dao.entity.Role;
import com.srct.service.account.dao.mapper.RoleDao;
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
public class RoleService extends BaseRepository<RoleDao, Role> {

    @Resource
    private RoleDao roleDao;


    public int updateBatch(List<Role> list) {
        return roleDao.updateBatch(list);
    }


    public int batchInsert(List<Role> list) {
        return roleDao.batchInsert(list);
    }


    public int insertOrUpdate(Role record) {
        return roleDao.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Role record) {
        return roleDao.insertOrUpdateSelective(record);
    }

    @Override
    protected RoleDao getDao() {
        return roleDao;
    }
}

