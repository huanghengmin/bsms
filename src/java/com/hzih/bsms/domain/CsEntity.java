package com.hzih.bsms.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-24
 * Time: 下午3:22
 * To change this template use File | Settings | File Templates.
 */
public class CsEntity {
    private int id;
    /**
     * 工作时间
     */
    private String workTime;
    /**
     * 源ip
     */
    private String sourceIp;
    /**
     * 源端口
     */
    private String sourcePort;
    /**
     * 目标ip
     */
    private String distIp;
    /**
     * 目标端口
     */
    private String distPort;
    /**
     * csEntity 是否正在运行,default is not run
     */
    private int isRun;
    /*csEntity 是否要运行 default false = 0*/
    private int flagRun;
    /*is tcp or udp tcp is 0,udp is 1*/
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(String sourcePort) {
        this.sourcePort = sourcePort;
    }

    public String getDistIp() {
        return distIp;
    }

    public void setDistIp(String distIp) {
        this.distIp = distIp;
    }

    public String getDistPort() {
        return distPort;
    }

    public void setDistPort(String distPort) {
        this.distPort = distPort;
    }

    public int getRun() {
        return isRun;
    }

    public void setRun(int run) {
        isRun = run;
    }

    public int getFlagRun() {
        return flagRun;
    }

    public void setFlagRun(int flagRun) {
        this.flagRun = flagRun;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
