package com.srct.service.account.dao.entity;

public class UserWithRoleList extends User {
    private java.util.List<Role> roleList;

    public java.util.List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(java.util.List<Role> roleList) {
        this.roleList = roleList;
    }
}
