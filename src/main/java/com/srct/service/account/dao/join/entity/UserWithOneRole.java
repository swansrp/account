package com.srct.service.account.dao.join.entity;

import com.srct.service.account.dao.common.entity.Role;
import com.srct.service.account.dao.common.entity.User;
import lombok.Data;

/**
 * Title: UserWithOneRole
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019/10/6 9:27
 * @description Project Name: Grote
 * @Package: com.srct.service.account.dao.join.entity
 */
@Data
public class UserWithOneRole extends User {
    private Role role;
}
