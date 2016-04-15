package com.hzih.bsms.domain;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 上午10:20
 * To change this template use File | Settings | File Templates.
 */
public class CaPermission {
    private int id;
    private String url;
    private Set<CaRole> roles;

    public Set<CaRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<CaRole> roles) {
        this.roles = roles;
    }

    public CaPermission() {
    }

    public CaPermission(String url) {

        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
