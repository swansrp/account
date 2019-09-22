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

import com.srct.service.account.dao.entity.Role;
import com.srct.service.account.dao.entity.User;
import com.srct.service.account.dao.entity.UserRole;
import com.srct.service.account.dao.repository.RoleService;
import com.srct.service.account.dao.repository.UserRoleService;
import com.srct.service.account.dao.repository.UserService;
import com.srct.service.account.service.AuthRoleService;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.constant.ErrCodeSys;
import com.srct.service.utils.ReflectionUtil;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindUserRole(String userId, Integer roleId) {
        User user = userService.getUserByUserId(userId);
        Validator.assertNotNull(user, ErrCodeSys.PA_PARAM_NULL, "用户名");
        Validator.assertFalse(roleService.existsWithPrimaryKey(roleId), ErrCodeSys.PA_DATA_NOT_EXIST, "角色ID");
        UserRole userRole = buildUserRole(user.getId(), roleId);
        userRoleService.insertOrUpdate(userRole);
    }

    @Override
    public List<Role> getRoleListByUserId(String userId) {
        User user = userService.getUserByUserId(userId);
        Validator.assertNotNull(user, ErrCodeSys.PA_PARAM_NULL, "用户名");
        List<UserRole> userRoleList = userRoleService.getUserRoleListByUserId(user.getId());
        List<Integer> userRoleIdList = ReflectionUtil.getFieldList(userRoleList, "roleId", Integer.class);
        return roleService.selectByPropertyList("id", userRoleIdList);
    }

    @Override
    public Role getRoleByUserId(String userId) {
        User user = userService.getUserByUserId(userId);
        Validator.assertNotNull(user, ErrCodeSys.PA_PARAM_NULL, "用户名");
        UserRole userRole = userRoleService.getUserRoleByUserId(user.getId());
        return roleService.selectByPrimaryKey(userRole.getRoleId());
    }

    private UserRole buildUserRole(Integer userId, Integer roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRole.setValid(DataSourceCommonConstant.DATABASE_COMMON_VALID);
        return userRole;
    }
}
