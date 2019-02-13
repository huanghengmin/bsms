package com.hzih.bsms.web.action.ldap;

import com.hzih.bsms.service.LogService;
import com.hzih.bsms.syslog.SysLogSend;
import com.hzih.bsms.web.SessionUtils;
import com.hzih.bsms.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: hhm
 * Date: 12-11-8
 * Time: 下午11:11
 * To change this template use File | Settings | File Templates.
 */
public class LdapConfigAction extends ActionSupport {
    private Logger logger = Logger.getLogger(LdapConfigAction.class);

    private LogService logService;

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    /**
     * 保存配置
     * @return
     * @throws java.io.IOException
     */
    public String save() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json = null;
        String msg = null;
        String host = request.getParameter("host");
        String port = request.getParameter("port");
        String base = request.getParameter("base");
        String adm = request.getParameter("adm");
        String pwd = request.getParameter("pwd");
        boolean flag = LdapXMLUtils.save(host,Integer.parseInt(port),adm,pwd,base);
        if(flag){
            msg = "LDAP配置保存成功";
            json = "{success:true,msg:'" + msg + "'}";
            logger.info("管理员" + SessionUtils.getAccount(request).getUserName() + ",操作时间:" + new Date() + ",操作信息:" + msg);
            //SysLogSend.sysLog("管理员" + SessionUtils.getAccount(request).getUserName() + ",操作时间:" + new Date() + ",操作信息:" + msg);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "LDAP配置", msg);

        }else {
            msg = "LDAP配置保存失败";
            json = "{success:false,msg:'" + msg + "'}";
            logger.info("管理员" + SessionUtils.getAccount(request).getUserName() + ",操作时间:" + new Date() + ",操作信息:" + msg);
            //SysLogSend.sysLog("管理员" + SessionUtils.getAccount(request).getUserName() + ",操作时间:" + new Date() + ",操作信息:" + msg);
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "LDAP配置", msg);
        }
        actionBase.actionEnd(response,json,result);
        return null;
    }

    /**
     * ldap 连通性测试
     * @return
     * @throws java.io.IOException
     */
    public String ldapConnections() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json = null;
        String msg = null;
        String host = request.getParameter("host");
        String port = request.getParameter("port");
        String base = request.getParameter("base");
        String adm = request.getParameter("adm");
        String pwd = request.getParameter("pwd");
        LdapUtils ldapUtils = new LdapUtils();
        boolean flag =  ldapUtils.ldapConnections(host,Integer.parseInt(port),adm,pwd);
        if(flag){
            msg = "LDAP服务器正常连通";
            json ="{success:true,msg:'"+msg+"'}";
        }else {
            msg = "LDAP服务器异常";
            json = "{success:false,msg:'"+msg+"'}";
        }
        actionBase.actionEnd(response,json,result);
        return null;
    }

    /**
     * 查找
     * @return
     */
    public String find(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        int totalCount =0;
        StringBuilder sb = new StringBuilder();
        jsonResult(sb);
        totalCount = totalCount+1;
        StringBuilder json=new StringBuilder("{totalCount:"+totalCount+",root:[");
        json.append(sb.toString());
        json.append("]}");
        try {
            actionBase.actionEnd(response,json.toString(),result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回JSON数据格式
     * @param sb
     */
    private void jsonResult(StringBuilder sb) {
        String adm = LdapXMLUtils.getValue(LdapXMLUtils.adm);
        if(adm==null){
            adm = "";
        }
        String pwd = LdapXMLUtils.getValue(LdapXMLUtils.pwd);
        if(pwd==null){
            pwd = "";
        }
        String base = LdapXMLUtils.getValue(LdapXMLUtils.base);
        if(base==null){
            base = "";
        }
        String host = LdapXMLUtils.getValue(LdapXMLUtils.host);
        if(host==null){
            host = "";
        }
        String port = LdapXMLUtils.getValue(LdapXMLUtils.port);
        if(port==null){
            port = "";
        }
        sb.append("{");
        sb.append("adm:'"+ adm+"',");
        sb.append("pwd:'"+ pwd+"',");
        sb.append("base:'"+base+"',");
        sb.append("host:'"+ host+"',");
        sb.append("port:'"+ port+"'");
        sb.append("}");
    }
}
