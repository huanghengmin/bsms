package com.hzih.syslog;


import com.hzih.bsms.utils.StringContext;
import com.hzih.syslog.code.SyslogCodecFactory;
import com.hzih.syslog.format.LogFormatFactory;
import com.hzih.syslog.format.OpenVpnLog;
import com.inetec.common.util.Proc;
import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;
import org.apache.mina.transport.socket.DatagramSessionConfig;
import org.apache.mina.transport.socket.nio.NioDatagramAcceptor;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;

/**
 * Created by IntelliJ IDEA.
 * User: bluesky
 * Date: 2009-11-7
 * Time: 23:39:55
 * To change this template use File | Settings | File Templates.
 */
public class SyslogServer extends Thread {
    public static final Logger logger = Logger.getLogger(SyslogServer.class);
    private int timeout = 1;
    private boolean m_runing = false;
    private NioDatagramAcceptor acceptor;

    private SyslogHandler handler;
    private String charset;
    private ExecutorService filterExecutor;
    private ExecutorService acceptExecutor;
    private String sysloghost;
    private int syslogport;
    public BlockingQueue<String> query;

    public boolean isRun() {
        return m_runing;
    }

    public void processSyslog(SyslogMessage syslog) {
       OpenVpnLog vpnLog = (OpenVpnLog) LogFormatFactory.getLogFormat(syslog.getMessage(), "info");
        try {
            //只更新当前用的信息
          /*  String  command = "sh " +StringContext.systemPath+"/script/control.sh "+
                    vpnLog.getUsername()+" "+
                    vpnLog.getVpnip()+" ";
            Proc proc = new Proc();
            proc.exec(command);
            logger.info(command);
            if(proc.getErrorOutput().contains("error")){
                logger.info(proc.getErrorOutput());
            }*/
           //查询出以前使用相同ip的用户，清除配置文件
            ClientDao clientDao = new ClientDao();
            clientDao.modify(vpnLog);

            String  command = "sh " +StringContext.systemPath+"/bsshell/reconfigure_squid.sh "+
                    vpnLog.getUsername()+" "+
                    vpnLog.getVpnip()+" ";
            Proc proc = new Proc();
            proc.exec(command);
//            logger.info(command);
            if(proc.getErrorOutput().contains("error")){
                logger.info(proc.getErrorOutput());
            }
        } catch (Exception e) {
            logger.info(e);
            logger.error("更新用户上下线信息出错!");
        }
    }

    public void close() {
        m_runing = false;
        if (acceptor != null) {
            if (sysloghost != null)
                acceptor.unbind(new InetSocketAddress(sysloghost, syslogport));
            else {
                acceptor.unbind(new InetSocketAddress(syslogport));
            }

            acceptor.dispose();
            if (filterExecutor != null)
                filterExecutor.shutdown();
            if (acceptExecutor != null) {
                acceptExecutor.shutdown();

            }
        }
        logger.info("Syslog service Run stop.port:" + syslogport);
    }


    public void config(String host, int port, String charset) {
        sysloghost = host;
        syslogport = port;
        this.charset = charset;
        this.filterExecutor = new OrderedThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1);
        this.acceptExecutor = new OrderedThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1);
    }


    public void run() {
        m_runing = true;
        try {
            startSyslog();
        } catch (Exception e) {
            logger.warn("Syslog  service Run error.port:" + syslogport, e);
        }
    }

    private void startSyslog() throws Exception {
        // Create UDP/IP acceptor.
        acceptor = new NioDatagramAcceptor();
        DatagramSessionConfig dcfg = acceptor.getSessionConfig();
        dcfg.setReuseAddress(true);
        acceptor.getFilterChain().addLast("threadPool", new ExecutorFilter(filterExecutor));//
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new SyslogCodecFactory(charset)));
        //acceptor.getSessionConfig().setKeepAlive(true);
        handler = new SyslogHandler(this);
        acceptor.setHandler(handler);

        acceptor.getSessionConfig().setBothIdleTime(timeout * 60);
        //acceptor.getSessionConfig().setSoLinger(0);
        //acceptor.setReuseAddress(true);
        if (sysloghost != null)
            acceptor.bind(new InetSocketAddress(sysloghost, syslogport));
        else
            acceptor.bind(new InetSocketAddress(syslogport));
    }


    public static void main(String arg[]) throws Exception {
        SyslogServer syslog = new SyslogServer();
        syslog.config(null, 1514, "utf-8");
//        syslog.setRemtoeServer("192.168.1.5", 1514);
        syslog.start();
        while (true) {
//            syslog.info("ddd","1212","2525");
            Thread.sleep(60 * 1000);
        }
    }
}
