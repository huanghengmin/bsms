package com.hzih.syslog.format;

public interface ILogFormat {
    public static final String S_Deviceid = "deviceid";
    public static final String S_ip = "ip";

    public void process(String log, String level);

    public boolean validate(String log);

    public long getIn_Flux();

    public long getOut_Flux();




}
