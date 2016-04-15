package com.hzih.bsms.web.servlet;

import com.hzih.bsms.domain.SafePolicy;
import com.hzih.bsms.service.SafePolicyService;
import com.hzih.bsms.constant.AppConstant;
import com.hzih.bsms.constant.ServiceConstant;
import com.hzih.bsms.syslog.SysLogSendService;
import com.hzih.bsms.web.SiteContext;
import com.hzih.bsms.web.action.cs.ShellUtils;
import com.hzih.bsms.web.action.socat.HttpsProcess;
import com.hzih.bsms.web.action.socat.HttpsServer;
import com.hzih.bsms.web.thread.SystemStatusService;
import com.hzih.myjfree.RunMonitorInfoList;
import com.hzih.myjfree.RunMonitorLiuliangBean2List;
import com.hzih.ssl.core.http.HttpsAuthServer;
import com.hzih.ssl.core.util.ConfigXML;
import com.hzih.ssl.jdbc.NginxLogsTask;
import com.hzih.ssl.jdbc.TimerUtil;
import com.hzih.syslog.SyslogServer;
import jsonrpchander.server.TcpServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SiteContextLoaderServlet extends DispatcherServlet {
    public static Map<HttpsProcess, HttpsProcess> httpsProcesses = new HashMap<>();
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Log log = LogFactory.getLog(SiteContextLoaderServlet.class);
    public static boolean isRunSysLogService = false;
    public static SysLogSendService sysLogSendService = new SysLogSendService();

    //启动syslog服务
    public void runSysLogSend() {
        log.info("启动syslog日志!");
        if (SiteContextLoaderServlet.isRunSysLogService) {
            return;
        } else {
            sysLogSendService.init();
            Thread thread = new Thread(sysLogSendService);
            thread.start();
            SiteContextLoaderServlet.isRunSysLogService = true;
        }
    }

    public void runSysLogRevice() {
        new Thread() {
            public void run() {
                SyslogServer syslog = new SyslogServer();
                syslog.config(null, 1514, "utf-8");
                syslog.start();
                log.info("启动syslog接收!");
                while (true) {
                    try {
                        Thread.sleep(60 * 1000);
                    } catch (InterruptedException e) {
                        log.error("启动syslog接收出错!");
                    }
                }
            }
        }.start();
    }

    //启动安全服务监听
    public void startService() {
        log.info("启动安全服务监听!");
        HttpsAuthServer server = new HttpsAuthServer();
        server.init(ConfigXML.readBindAddress(), Integer.parseInt(ConfigXML.readBindPort()), ConfigXML.readKeystore(), ConfigXML.readKeystorePwd());
        server.start();
    }

    //启动nginx日志处理线程
    public void startNginxLogService() {
        log.info("启动nginx日志处理!");
        TimerUtil timerUtil = new TimerUtil();
        NginxLogsTask task = new NginxLogsTask();
        timerUtil.startMyTask(task, 1, 0, 0);
    }

    //处理日志
//    public void hadlerNginxLogs(){
//        HandlerNginxLogs handlerNginxLogs = new HandlerNginxLogs();
//        String path = handlerNginxLogs.getFilePath();
//            while (true)   {
//                handlerNginxLogs.readLine(path);
//            try {
//                Thread.sleep(20*1000);
//            } catch (InterruptedException e) {
//               log.error(e.getMessage());
//            }
//        }
//    }

    //启动https代理服务
    public void start_https() {
        HttpsServer.start();
    }


    /*start jsonrpc service*/
    public void JSONRPCService() {
        TcpServer tcpServer = new TcpServer();
        tcpServer.setPort(1155);
        tcpServer.start();
        log.info("jsonrpc service start!");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        SiteContext.getInstance().contextRealPath = config.getServletContext().getRealPath("/");
        // set constants value to app context
        servletContext.setAttribute("appConstant", new AppConstant());
        SafePolicyService service = (SafePolicyService) context.getBean(ServiceConstant.SAFEPOLICY_SERVICE);
        SafePolicy data = service.getData();
        SiteContext.getInstance().safePolicy = data;
        //enable ip forward
        ShellUtils.enable_ipforward();
        //jsonrpc服务
//        JSONRPCService();
        //启动https代理
//        start_https();
        //启动syslog
        runSysLogSend();
        //接收VPN 日志
        runSysLogRevice();
        //启动安全服务
//        startService();
        //读取网卡流量

//        new RunMonitorInfoList().start();
//        new RunMonitorLiuliangBean2List().start();

        new SystemStatusService().start();
        //启动nginx日志处理线程
//        startNginxLogService();
        //处理日志
//        hadlerNginxLogs();
    }

    @Override
    public ServletConfig getServletConfig() {
        // do nothing
        return null;
    }

    @Override
    public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
        // do nothing
    }

    @Override
    public String getServletInfo() {
        // do nothing
        return null;
    }

    @Override
    public void destroy() {
        // do nothing
    }
}
