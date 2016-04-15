package com.hzih.bsms.domain;

import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
public class CaRole {
    public Set<CaPermission> getCaPermissions() {
        return caPermissions;
    }
    
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setCaPermissions(Set<CaPermission> caPermissions) {
        this.caPermissions = caPermissions;
    }

    Set<CaPermission> caPermissions;

    Set<CaUser> caUsers;

    public Set<CaUser> getCaUsers() {
        return caUsers;
    }

    public void setCaUsers(Set<CaUser> caUsers) {
        this.caUsers = caUsers;
    }

    int id;

    String name;

    String description;

    Date createdTime;

    Date modifiedTime;

    public CaRole() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
