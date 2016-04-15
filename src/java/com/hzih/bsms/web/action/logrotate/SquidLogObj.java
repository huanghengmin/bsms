package com.hzih.bsms.web.action.logrotate;

/**
 * Created by hhm on 2014/12/10.
 */
public class SquidLogObj {
    private String date;
    private String host;
    private String result_code;
    private String request_bytes;
    private String reply_bytes;
    private String request_msg;
    private String client_msg;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getRequest_bytes() {
        return request_bytes;
    }

    public void setRequest_bytes(String request_bytes) {
        this.request_bytes = request_bytes;
    }

    public String getReply_bytes() {
        return reply_bytes;
    }

    public void setReply_bytes(String reply_bytes) {
        this.reply_bytes = reply_bytes;
    }

    public String getRequest_msg() {
        return request_msg;
    }

    public void setRequest_msg(String request_msg) {
        this.request_msg = request_msg;
    }

    public String getClient_msg() {
        return client_msg;
    }

    public void setClient_msg(String client_msg) {
        this.client_msg = client_msg;
    }
}
