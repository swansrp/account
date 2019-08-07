/**
 * Title: PermitCoreServiceImpl.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-31 16:04
 * @description Project Name: Grote
 * @Package: com.srct.service.account.service.impl
 */
package com.srct.service.account.service.impl;

import com.srct.service.account.bo.permit.PermitBO;
import com.srct.service.account.constants.token.TokenItemConst;
import com.srct.service.account.dao.entity.Permit;
import com.srct.service.account.dao.repository.PermitService;
import com.srct.service.account.dao.repository.RolePermitService;
import com.srct.service.account.dao.repository.UserPermitService;
import com.srct.service.account.service.PermitCoreService;
import com.srct.service.account.service.TokenService;
import com.srct.service.account.vo.permit.PermitRes;
import com.srct.service.account.vo.permit.PermitTreeRes;
import com.srct.service.config.db.DataSourceCommonConstant;
import com.srct.service.utils.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PermitCoreServiceImpl implements PermitCoreService {

    @Resource
    private UserPermitService userPermitService;
    @Resource
    private PermitService permitService;
    @Resource
    private TokenService tokenService;
    @Resource
    private RolePermitService rolePermitService;

    @Override
    public List<PermitRes> getAllPermit() {
        List<Permit> permitList = permitService.selectAll();
        return ReflectionUtil.copyList(permitList, PermitRes.class);
    }

    @Override
    public List<PermitTreeRes> getAllPermitTree(String clientType) {
        Map<String, Object> propertyMap = new HashMap<>(2);
        propertyMap.put("valid", DataSourceCommonConstant.DATABASE_COMMON_VALID);
        if (StringUtils.isNotBlank(clientType)) {
            propertyMap.put("clientType", clientType);
        }
        List<Permit> permitList = permitService.selectByPropertyMap(propertyMap);
        List<PermitBO> permitResList = ReflectionUtil.copyList(permitList, PermitBO.class);
        return buildPermitTree(permitResList);
    }

    @Override
    public List<PermitBO> getPermitList(String userId, String clientType) {
        List<Permit> permitList = userPermitService.getPermitListByUserIdAndClientType(userId, clientType);
        return ReflectionUtil.copyList(permitList, PermitBO.class);
    }

    @Override
    public List<PermitTreeRes> getPermitTree() {
        List<PermitBO> permitList = tokenService.getItem(TokenItemConst.PERMIT_TREE.name(), List.class, PermitBO.class);
        return buildPermitTree(permitList);
    }

    @Override
    public List<PermitTreeRes> getRolePermitTree(List<Integer> roleIdList) {
        List<Integer> permitIdList =
                rolePermitService.selectFieldListByPropertyList("roleId", roleIdList, "permitId", Integer.class);
        List<Permit> permitList = permitService.selectByPropertyList("id", permitIdList);
        List<PermitBO> permitResList = ReflectionUtil.copyList(permitList, PermitBO.class);
        return buildPermitTree(permitResList);
    }

    @Override
    public List<PermitTreeRes> getUserPermitTree(String userId) {
        List<PermitBO> boList = getPermitList(userId, null);
        return buildPermitTree(boList);
    }

    private List<PermitTreeRes> buildPermitTree(List<PermitBO> permitList) {
        List<PermitTreeRes> res = new ArrayList<>();
        List<PermitBO> parentMenuList =
                permitList.stream().filter(permit -> permit.getParentPermitId() == null).collect(Collectors.toList());
        parentMenuList.forEach(parentMenu -> res.add(buildPermitTree(parentMenu, permitList)));
        return res;
    }

    private PermitTreeRes buildPermitTree(PermitBO parentMenu, List<PermitBO> permitList) {
        PermitTreeRes menu = new PermitTreeRes();
        menu.setMenu(parentMenu);
        List<PermitBO> menuList =
                permitList.stream().filter(permit -> parentMenu.getId().equals(permit.getParentPermitId()))
                        .collect(Collectors.toList());
        menuList.forEach(subMenu -> menu.getSubMenu().add(buildPermitTree(subMenu, permitList)));
        return menu;
    }
}
