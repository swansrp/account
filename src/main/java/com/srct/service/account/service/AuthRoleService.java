/**
 * Title: AuthRoleService.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-9-15 14:07
 * @description Project Name: Grote
 * @Package: com.srct.service.account.service
 */
package com.srct.service.account.service;

import com.srct.service.account.dao.common.entity.Role;

import java.util.List;

public interface AuthRoleService {
    /**
     * 绑定用户角色.
     *
     * @param userId 用户登录名
     * @param roleId 角色Id
     * @return
     */
    void bindUserRole(String userId, Integer roleId);

    /**
     * 解除绑定用户角色.
     *
     * @param userId 用户登录名
     * @param roleId 角色Id
     * @return
     */
    void unbindUserRole(String userId, Integer roleId);

    /**
     * 获取用户角色列表(多角色).
     *
     * @param userId 用户登录名
     * @return 登录role角色列表
     */
    List<Role> getRoleListByUserId(String userId);

    /**
     * 获取用户角色.
     *
     * @param userId 用户登录名
     * @return 登录role角色
     */
    Role getRoleByUserId(String userId);

    /**
     * 获取下属role 列表
     *
     * @param parentRoleId
     * @return
     */
    List<Role> getSubRoleList(Integer parentRoleId);
}
