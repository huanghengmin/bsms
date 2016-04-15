package com.hzih.bsms.syslog.format;

public class LogFormatFactory {

    public static ILogFormat getLogFormat(String log,String leve) {
        ILogFormat result = null;
        if(new OpenVpnLog().validate(log)){
            result=new OpenVpnLog();
            result.process(log, leve);
        }
        return result;
    }
}
