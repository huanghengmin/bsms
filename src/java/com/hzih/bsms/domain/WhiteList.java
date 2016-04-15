package com.hzih.bsms.domain;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 下午5:06
 * To change this template use File | Settings | File Templates.
 */
public class WhiteList {
    private String url;
//    private int status;

//    public int getStatus() {
//        return status;
//    }

//    public WhiteList(String url, int status) {
//        this.url = url;
//        this.status = status;
//    }

//    public void setStatus(int status) {
//        this.status = status;
//    }

//    @Override
//    public String toString() {
//        return "WhiteList{" +
//                "url='" + url + '\'' +
//                ", status=" + status +
//                '}';
//    }

    @Override
    public String toString() {
        return "WhiteList{" +
                "url='" + url + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WhiteList() {

    }

    public WhiteList(String url) {

        this.url = url;
    }
}
