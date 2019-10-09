package com.srct.service.account.dao.join.entity;

import com.srct.service.account.dao.common.entity.Role;
import com.srct.service.account.dao.common.entity.User;
import lombok.Data;

import java.util.List;

/**
 * Title: RoleAndUserList
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/5 22:41
 * @description Project Name: Grote
 * @Package: com.srct.service.account.dao.join.entity
 */
@Data
public class UserWithRoleList extends User {
    private List<Role> roleList;
}
