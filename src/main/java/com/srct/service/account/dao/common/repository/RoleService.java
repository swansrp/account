package com.srct.service.account.dao.common.repository;

import com.srct.service.account.dao.common.entity.Role;
import com.srct.service.account.dao.common.mapper.RoleDao;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.frame.BaseRepository;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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

    public List<Role> selectRoleByParentRoleId(Integer roleId) {
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("valid", DataSourceCommonConstant.DATABASE_COMMON_VALID);
        criteria.andEqualTo("parentRoleId", roleId);
        return roleDao.selectByExample(example);
    }

    @Override
    protected RoleDao getDao() {
        return roleDao;
    }
}

