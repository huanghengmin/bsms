package com.hzih.bsms.cs;

import com.hzih.bsms.utils.StringContext;
import com.inetec.common.util.OSInfo;
import com.inetec.common.util.Proc;

/**
 * Created by Administrator on 14-12-25.
 */
public class TcpShellUtils {
    public static void add(String bindIp,String bindPort,String accessIp,String accessPort){
        Proc proc = new Proc();
        String command = null;
        if (OSInfo.getOSInfo().isLinux()) {
            command = StringContext.systemPath + "/bsshell/cs_tcp_add.sh " +
                    bindIp + " " +
                    bindPort + " " +
                    accessIp + " " +
                    accessPort;
        } else {
            command = StringContext.systemPath + "/bsshell/cs_tcp_add.sh " +
                    bindIp + " " +
                    bindPort + " " +
                    accessIp + " " +
                    accessPort;
        }
        proc.exec(command);
    }


    public static void del(String bindIp,String bindPort,String accessIp,String accessPort){
        Proc proc = new Proc();
        String command = null;
        if (OSInfo.getOSInfo().isLinux()) {
            command = StringContext.systemPath + "/bsshell/cs_tcp_del.sh " +
                    bindIp + " " +
                    bindPort + " " +
                    accessIp + " " +
                    accessPort;
        } else {
            command = StringContext.systemPath + "/bsshell/cs_tcp_del.sh " +
                    bindIp + " " +
                    bindPort + " " +
                    accessIp + " " +
                    accessPort;
        }
        proc.exec(command);
    }

}
