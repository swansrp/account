package com.srct.service.account.dao.repository;

import com.srct.service.account.dao.entity.UserRole;
import com.srct.service.account.dao.mapper.UserRoleDao;
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

    public UserRole getUserRoleByRoleId(Integer roleId) {
        return getUserRoleByRoleIdAndUserId(roleId, null);
    }

    public List<UserRole> getUserRoleListByRoleId(Integer roleId) {
        return getUserRoleListByRoleIdAndUserId(roleId, null);
    }

    public UserRole getUserRoleByUserId(Integer userId) {
        return getUserRoleByRoleIdAndUserId(null, userId);
    }

    public List<UserRole> getUserRoleListByUserId(Integer userId) {
        return getUserRoleListByRoleIdAndUserId(null, userId);
    }

    public UserRole getUserRoleByRoleIdAndUserId(Integer roleId, Integer userId) {
        Example example = buildExample(roleId, userId);
        return selectOneByExample(example);
    }

    public List<UserRole> getUserRoleListByRoleIdAndUserId(Integer roleId, Integer userId) {
        Example example = buildExample(roleId, userId);
        return selectByExample(example);
    }

    private Example buildExample(Integer roleId, Integer userId) {
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        if (roleId != null) {
            criteria.andEqualTo("roleId", roleId);
        }
        if (userId != null) {
            criteria.andEqualTo("userId", userId);
        }
        criteria.andEqualTo("valid", DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return example;
    }


}
