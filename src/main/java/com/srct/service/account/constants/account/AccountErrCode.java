/**
 * Title: AccountErrCode.java
 * Description: Copyright: Copyright (c) 2019 Company: BHFAE
 *
 * @author Sharp
 * @date 2019-7-31 10:39
 * @description Project Name: Grote
 * @Package: com.srct.service.account.constants
 */
package com.srct.service.account.constants.account;

import com.srct.service.constant.ErrCode;

public enum AccountErrCode implements ErrCode {
    // 用户名不存在
    AC_USER_NOT_EXISTED,
    // 用户名已存在
    AC_USER_ALREADY_EXISTED,
    // 密码不正确
    AC_PASSWORD_MISTAKE,
    // 用户已锁定
    AC_LOCK,
    // 没有权限,请联系管理员
    AC_NO_PERMIT_TREE
}
