package com.srct.service.account.dao.join.entity;

import com.srct.service.account.dao.common.entity.Role;
import com.srct.service.account.dao.common.entity.User;
import lombok.Data;

import java.util.List;

/**
 * Title: RoleWithUserList
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/5 21:50
 * @description Project Name: Grote
 * @Package: com.srct.service.account.dao.join.entity
 */
@Data
public class RoleWithUserList extends Role {
    private List<User> userList;
}
