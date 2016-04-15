package com.hzih.bsms.domain;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 上午10:20
 * To change this template use File | Settings | File Templates.
 */
public class CaUserRole implements Serializable {
    private int  ca_user_id;
    private int ca_role_id;

    public CaUserRole() {
    }

    public CaUserRole(int id, int roleId) {
        this.ca_user_id = id;
        this.ca_role_id=roleId;
    }

    public int getCa_user_id() {

        return ca_user_id;
    }

    public void setCa_user_id(int ca_user_id) {
        this.ca_user_id = ca_user_id;
    }

    public int getCa_role_id() {
        return ca_role_id;
    }

    public void setCa_role_id(int ca_role_id) {
        this.ca_role_id = ca_role_id;
    }
}
