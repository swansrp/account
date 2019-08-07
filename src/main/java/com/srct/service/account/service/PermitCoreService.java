/**
 * Title: PermitCoreService.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-31 16:03
 * @description Project Name: Grote
 * @Package: com.srct.service.account.service
 */
package com.srct.service.account.service;

import com.srct.service.account.bo.permit.PermitBO;
import com.srct.service.account.vo.permit.PermitRes;
import com.srct.service.account.vo.permit.PermitTreeRes;

import java.util.List;

public interface PermitCoreService {

    /**
     * 获取权限列表
     *
     * @return 获取全体权限列表
     */
    List<PermitRes> getAllPermit();

    /**
     * 获取权限树
     *
     * @param clientType 客户端类型
     * @return 获取全体权限树
     */
    List<PermitTreeRes> getAllPermitTree(String clientType);

    /**
     * 获取权限列表
     *
     * @param userId     用户id
     * @param clientType 客户端类型
     * @return 权限树
     */
    List<PermitBO> getPermitList(String userId, String clientType);

    /**
     * 获取权限树
     *
     * @return 权限树
     */
    List<PermitTreeRes> getPermitTree();

    /**
     * 获取权限树
     *
     * @param roleIdList 角色ID列表
     * @return 权限树
     */
    List<PermitTreeRes> getRolePermitTree(List<Integer> roleIdList);

    /**
     * 获取权限树
     *
     * @param userId 用户ID
     * @return 权限树
     */
    List<PermitTreeRes> getUserPermitTree(String userId);
}
