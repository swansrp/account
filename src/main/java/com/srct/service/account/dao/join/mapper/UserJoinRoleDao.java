package com.srct.service.account.dao.join.mapper;

import com.srct.service.account.dao.join.entity.RoleWithUserList;
import com.srct.service.account.dao.join.entity.UserWithRoleList;

/**
 * Title: UserJoinRole
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/5 21:47
 * @description Project Name: Grote
 * @Package: com.srct.service.account.dao.join.mapper
 */
public interface UserJoinRoleDao {

    /**
     * 根据roleId 获取所有该role的用户列表
     *
     * @param id
     * @return
     */
    RoleWithUserList selectRoleWithUserListByRoleId(Integer id);

    /**
     * 根据userId 获取所有该用户的角色列表
     *
     * @param userId
     * @return
     */
    UserWithRoleList selectUserWithRoleListByUserId(String userId);

    /**
     * 根据userId 获取所有该用户的角色列表
     *
     * @param phoneNumber
     * @return
     */
    UserWithRoleList selectUserWithRoleListByPhoneNumber(String phoneNumber);

}
