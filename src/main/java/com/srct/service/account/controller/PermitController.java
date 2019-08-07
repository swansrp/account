/**
 * Title: PermitController.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-8-2 14:54
 * @description Project Name: Grote
 * @Package: com.srct.service.account.controller
 */
package com.srct.service.account.controller;

import com.srct.service.account.service.PermitCoreService;
import com.srct.service.account.vo.permit.PermitTreeRes;
import com.srct.service.config.annotation.Auth;
import com.srct.service.config.response.CommonExceptionHandler;
import com.srct.service.config.response.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.srct.service.config.annotation.Auth.AuthType.GUEST;
import static com.srct.service.config.annotation.Auth.AuthType.NONE;

@Auth(role = GUEST)
@Api(value = "权限操作", tags = "权限操作")
@RestController("PermitController")
@RequestMapping(value = "")
public class PermitController {
    @Resource
    private PermitCoreService permitCoreService;

    @ApiOperation(value = "权限树", notes = "登录后准入")
    @RequestMapping(value = "/permitTree", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<PermitTreeRes>>.Resp> getPermitTree() {
        List<PermitTreeRes> res = permitCoreService.getPermitTree();
        return CommonExceptionHandler.generateResponse(res);
    }

    @Auth(role = NONE)
    @ApiOperation(value = "获取全部权限列表", notes = "系统管理员准入")
    @RequestMapping(value = "/allPermitTree", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<PermitTreeRes>>.Resp> getAllPermitTree(
            @RequestParam(value = "clientType", required = false) String clientType) {
        List<PermitTreeRes> res = permitCoreService.getAllPermitTree(clientType);
        return CommonExceptionHandler.generateResponse(res);
    }

    @Auth(role = NONE)
    @ApiOperation(value = "获取角色权限列表", notes = "系统管理员准入")
    @RequestMapping(value = "/rolePermitTree", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<PermitTreeRes>>.Resp> getRolePermitTree(
            @RequestParam(value = "roleIdList") List<Integer> roleIdList) {
        List<PermitTreeRes> res = permitCoreService.getRolePermitTree(roleIdList);
        return CommonExceptionHandler.generateResponse(res);
    }

    @Auth(role = NONE)
    @ApiOperation(value = "获取用户权限列表", notes = "系统管理员准入")
    @RequestMapping(value = "/userPermitTree", method = RequestMethod.GET)
    public ResponseEntity<CommonResponse<List<PermitTreeRes>>.Resp> getUserPermitTree(String userId) {
        List<PermitTreeRes> res = permitCoreService.getUserPermitTree(userId);
        return CommonExceptionHandler.generateResponse(res);
    }
}
