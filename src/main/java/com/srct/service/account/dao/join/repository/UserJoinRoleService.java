package com.srct.service.account.dao.join.repository;

import com.srct.service.account.dao.join.entity.RoleWithUserList;
import com.srct.service.account.dao.join.entity.UserWithOneRole;
import com.srct.service.account.dao.join.entity.UserWithRoleList;
import com.srct.service.account.dao.join.mapper.UserJoinRoleDao;
import com.srct.service.constant.ErrCodeSys;
import com.srct.service.utils.BeanUtil;
import com.srct.service.validate.Validator;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Title: UserJoinRoleService
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/5 21:53
 * @description Project Name: Grote
 * @Package: com.srct.service.account.dao.join.repository
 */
@Service
public class UserJoinRoleService {

    @Resource
    private UserJoinRoleDao userJoinRoleDao;

    public RoleWithUserList getRoleWithUserList(Integer roleId) {
        return userJoinRoleDao.selectRoleWithUserListByRoleId(roleId);
    }

    public UserWithOneRole getUserWithOneRoleByUserId(String userId) {
        UserWithRoleList userWithRoleList = getUserWithRoleListByUserId(userId);
        if (CollectionUtils.isNotEmpty(userWithRoleList.getRoleList()) && userWithRoleList.getRoleList().size() > 1) {
            Validator.assertException(ErrCodeSys.SYS_ERR_MSG, "角色不唯一");
        }
        return buildUserWithOneRole(userWithRoleList);
    }

    public UserWithRoleList getUserWithRoleListByUserId(String userId) {
        return userJoinRoleDao.selectUserWithRoleListByUserId(userId);
    }

    private UserWithOneRole buildUserWithOneRole(UserWithRoleList userWithRoleList) {
        UserWithOneRole userWithOneRole = new UserWithOneRole();
        BeanUtil.copyProperties(userWithRoleList, userWithOneRole);
        userWithOneRole.setRole(userWithRoleList.getRoleList().get(0));
        return userWithOneRole;
    }

    public UserWithOneRole getUserWithOneRoleByPhoneNumber(String phoneNumber) {
        UserWithRoleList userWithRoleList = getUserWithRoleListByPhoneNumber(phoneNumber);
        if (CollectionUtils.isNotEmpty(userWithRoleList.getRoleList()) && userWithRoleList.getRoleList().size() > 1) {
            Validator.assertException(ErrCodeSys.SYS_ERR_MSG, "角色不唯一");
        }
        return buildUserWithOneRole(userWithRoleList);
    }

    public UserWithRoleList getUserWithRoleListByPhoneNumber(String phoneNumber) {
        return userJoinRoleDao.selectUserWithRoleListByPhoneNumber(phoneNumber);
    }
}
