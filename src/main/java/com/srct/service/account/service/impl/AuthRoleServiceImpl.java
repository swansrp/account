/**
 * Title: RoleServiceImpl.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-9-15 14:10
 * @description Project Name: Grote
 * @Package: com.srct.service.account.service.impl
 */
package com.srct.service.account.service.impl;

import com.srct.service.account.dao.common.entity.Role;
import com.srct.service.account.dao.common.entity.User;
import com.srct.service.account.dao.common.entity.UserRole;
import com.srct.service.account.dao.common.repository.RoleService;
import com.srct.service.account.dao.common.repository.UserRoleService;
import com.srct.service.account.dao.common.repository.UserService;
import com.srct.service.account.dao.join.repository.UserJoinRoleService;
import com.srct.service.account.service.AuthRoleService;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.constant.ErrCodeSys;
import com.srct.service.validate.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class AuthRoleServiceImpl implements AuthRoleService {

    @Resource
    private UserService userService;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RoleService roleService;
    @Resource
    private UserJoinRoleService userJoinRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindUserRole(String userId, Integer roleId) {
        User user = userService.getUserByUserId(userId);
        Validator.assertNotNull(user, ErrCodeSys.PA_PARAM_NULL, "用户名");
        Validator.assertTrue(roleService.existsWithPrimaryKey(roleId), ErrCodeSys.PA_DATA_NOT_EXIST, "角色ID");
        UserRole userRole = buildUserRole(user.getId(), roleId);
        userRoleService.insertOrUpdate(userRole);
    }

    @Override
    public void unbindUserRole(String userId, Integer roleId) {
        User user = userService.getUserByUserId(userId);
        Validator.assertNotNull(user, ErrCodeSys.PA_PARAM_NULL, "用户名");
        UserRole userRole = userRoleService.getUserRoleByRoleIdAndUserId(roleId, user.getId());
        Validator.assertNotNull(userRole, ErrCodeSys.PA_PARAM_NULL, "该用户没有该角色");
        userRoleService.deleteByPrimaryKey(userRole);
    }

    @Override
    public List<Role> getRoleListByUserId(String userId) {
        return userJoinRoleService.getUserWithRoleListByUserId(userId).getRoleList();
    }

    @Override
    public Role getRoleByUserId(String userId) {
        return userJoinRoleService.getUserWithOneRoleByUserId(userId).getRole();
    }

    @Override
    public List<Role> getSubRoleList(Integer parentRoleId) {
        return roleService.selectRoleByParentRoleId(parentRoleId);
    }

    private UserRole buildUserRole(Integer userId, Integer roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRole.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return userRole;
    }
}
