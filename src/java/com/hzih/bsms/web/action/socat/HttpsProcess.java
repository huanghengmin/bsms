package com.hzih.bsms.web.action.socat;

import com.inetec.common.util.OSInfo;
import org.apache.commons.exec.*;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-20
 * Time: 下午6:06
 * To change this template use File | Settings | File Templates.
 */

public class HttpsProcess implements Serializable  {
    public static Logger logger = Logger.getLogger(HttpsProcess.class);
    private boolean isRuning = false;
    private Executor executor = new DefaultExecutor();
    private ExecuteWatchdog watchdog;
    private CommandLine command;
    private DefaultExecuteResultHandler Handler = new DefaultExecuteResultHandler();
    private ShutdownHookProcessDestroyer processDestroyer = new ShutdownHookProcessDestroyer();

    private String listen_ip;
    private int listen_port;
    private String proxy_ip;
    private int proxy_port;
    private String cafile;
    private String cert;


    @Override
    public String toString() {
        return "HttpsProcess{" +
                "isRuning=" + isRuning +
                ", executor=" + executor +
                ", watchdog=" + watchdog +
                ", command=" + command +
                ", listen_ip='" + listen_ip + '\'' +
                ", listen_port=" + listen_port +
                ", proxy_ip='" + proxy_ip + '\'' +
                ", proxy_port=" + proxy_port +
                ", cafile='" + cafile + '\'' +
                ", cert='" + cert + '\'' +
                '}';
    }

    public boolean isRun() {
        return isRuning;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HttpsProcess)) return false;
        HttpsProcess that = (HttpsProcess) o;
        if (listen_port != that.listen_port) return false;
        if (proxy_port != that.proxy_port) return false;
        if (!cafile.equals(that.cafile)) return false;
        if (!cert.equals(that.cert)) return false;
        if (!listen_ip.equals(that.listen_ip)) return false;
        if (!proxy_ip.equals(that.proxy_ip)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = listen_ip.hashCode();
        result = 31 * result + listen_port;
        result = 31 * result + proxy_ip.hashCode();
        result = 31 * result + proxy_port;
        result = 31 * result + cafile.hashCode();
        result = 31 * result + cert.hashCode();
        return result;
    }

    public void init(String listen_ip, int listen_port, String proxy_ip, int proxy_port,String cafile,String cert) {
        this.listen_ip = listen_ip;
        this.listen_port = listen_port;
        this.proxy_ip = proxy_ip;
        this.proxy_port = proxy_port;
        this.cafile = cafile;
        this.cert = cert;

        if (OSInfo.getOSInfo().isWin()) {
            command = new CommandLine("socat.exe");
        }
        if (OSInfo.getOSInfo().isLinux()) {
            command = new CommandLine("/usr/bin/socat");
        }
        command.addArgument(
                "openssl-listen:"+listen_port
                +",bind="+listen_ip+","
                +"verify=0"+","
                +"cafile="+cafile+","
                +"cert="+cert+","
                +"reuseaddr,fork"
                );
                command.addArgument( "SSL:" + proxy_ip
                + ":" + proxy_port+","
                +"cafile="+cafile+","
                +"cert="+cert);
    }

    public void start() {
        isRuning = true;
        executor.setExitValue(1);
        watchdog=new ExecuteWatchdog(-1);
        if (OSInfo.getOSInfo().isWin())
            executor.setWorkingDirectory(new File("E:/fartec/ichange/bs/ssl"));
        executor.setWatchdog(watchdog);
        executor.setProcessDestroyer(processDestroyer);
        Handler.onProcessComplete(0);
        try {
            executor.execute(command, Handler);
        } catch (IOException e) {
            logger.warn("HttpsProcess run IOException:", e);
        }
    }

    public void stop() {
        isRuning = false;
        watchdog.killedProcess();
        watchdog.destroyProcess();
        watchdog.stop();
    }

    public static void main(String arg[])throws Exception {
        HttpsProcess video = new HttpsProcess();
        video.init("192.168.1.8",1234,"192.168.1.115",8443,"ROOT.crt","User.pem");
        video.start();
        int n=0;
        while(n<1){
            n++;
            Thread.sleep(1000);
       }
        video.stop();
   }

}
