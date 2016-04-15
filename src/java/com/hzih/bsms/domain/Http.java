package com.hzih.bsms.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-22
 * Time: 下午2:19
 * To change this template use File | Settings | File Templates.
 */
public class Http {
    private int id;
    private String manager_ip;
    private String manager_port;
    private String proxy_ip;
    private String proxy_port;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManager_ip() {
        return manager_ip;
    }

    public void setManager_ip(String manager_ip) {
        this.manager_ip = manager_ip;
    }

    public String getManager_port() {
        return manager_port;
    }

    public void setManager_port(String manager_port) {
        this.manager_port = manager_port;
    }

    public String getProxy_ip() {
        return proxy_ip;
    }

    public void setProxy_ip(String proxy_ip) {
        this.proxy_ip = proxy_ip;
    }

    public String getProxy_port() {
        return proxy_port;
    }

    public void setProxy_port(String proxy_port) {
        this.proxy_port = proxy_port;
    }

}
