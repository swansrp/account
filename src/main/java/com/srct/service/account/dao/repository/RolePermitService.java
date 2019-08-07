package com.srct.service.account.dao.repository;

import com.srct.service.account.dao.entity.RolePermit;
import com.srct.service.account.dao.mapper.RolePermitDao;
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
public class RolePermitService extends BaseRepository<RolePermitDao, RolePermit> {

    @Resource
    private RolePermitDao rolePermitDao;


    public int updateBatch(List<RolePermit> list) {
        return rolePermitDao.updateBatch(list);
    }


    public int batchInsert(List<RolePermit> list) {
        return rolePermitDao.batchInsert(list);
    }


    public int insertOrUpdate(RolePermit record) {
        return rolePermitDao.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(RolePermit record) {
        return rolePermitDao.insertOrUpdateSelective(record);
    }

    @Override
    protected RolePermitDao getDao() {
        return rolePermitDao;
    }
}
