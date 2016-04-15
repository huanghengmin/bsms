package com.hzih.bsms.domain;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 上午10:20
 * To change this template use File | Settings | File Templates.
 */
public class CaRolePermission implements Serializable {
    private int ca_role_id;
    private int ca_permission_id;

    public CaRolePermission() {
    }

    public CaRolePermission(int id, int roleId) {
        this.ca_permission_id = id;

        this.ca_role_id = roleId;
    }

    public int getCa_role_id() {
        return ca_role_id;
    }

    public void setCa_role_id(int ca_role_id) {
        this.ca_role_id = ca_role_id;
    }

    public int getCa_permission_id() {
        return ca_permission_id;
    }

    public void setCa_permission_id(int ca_permission_id) {
        this.ca_permission_id = ca_permission_id;
    }
}
