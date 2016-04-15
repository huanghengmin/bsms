package com.hzih.bsms.web.action.openvpn;

import com.hzih.bsms.domain.CaPermission;
import com.hzih.bsms.domain.CaRole;
import com.hzih.bsms.domain.CaUser;
import com.hzih.bsms.service.CaUserService;
import com.hzih.bsms.syslog.SysLogSend;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-18
 * Time: 上午11:37
 * To change this template use File | Settings | File Templates.
 */
public class AccessControl extends ActionSupport {
    private Logger logger = Logger.getLogger(AccessControl.class);
    private CaUserService   caUserService;

    public CaUserService getCaUserService() {
        return caUserService;
    }

    public void setCaUserService(CaUserService caUserService) {
        this.caUserService = caUserService;
    }


    public String access()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String username = request.getParameter("username");
        String serial = request.getParameter("serial");
        String access = request.getParameter("access");
        String access_time = request.getParameter("access_time");
        String json = "false";
        boolean flag = false;
//        String json = "上报数据:\n"+
//                "用户名:"+username+"\n"+
//                "证书序列号:"+serial+"\n"+
//                "访问URL地址:"+access+"\n"+
//                "访问时间:"+access_time;
        CaUser caUser = null;
        try {
            caUser = caUserService.findByCn(username);
        } catch (Exception e) {
            logger.info("查找用户出错"+username);
        }
        if(null!=caUser&&null!=access&&!access.equals("")){
            Set<CaRole> caRoles =  caUser.getCaRoles();
            if(null!=caRoles){
                for (CaRole role:caRoles){
                    Set<CaPermission> caPermissions = role.getCaPermissions();
                    if(null!=caPermissions){
                            for (CaPermission caPermission:caPermissions){
                                String url = caPermission.getUrl();
                                if(url.equals("*")){
                                    flag = true;
                                }else if(url.startsWith("*")){
                                    String white = url.replace("*","");
                                    if(access.toLowerCase().endsWith(white.toLowerCase())||access.equalsIgnoreCase(white)){
                                        flag = true;
                                    }
                                } else if(url.endsWith("*")){
                                    String white = url.replace("*","");
                                    if(access.toLowerCase().startsWith(white.toLowerCase())||access.equalsIgnoreCase(white)){
                                        flag = true;
                                    }
                                } else if(url.contains("*")){
                                    String white = url.replace("*","");
                                    String[] split = url.split("\\*");
                                    if(access.toLowerCase().startsWith(split[0])&&access.toLowerCase().endsWith(split[1])||access.equalsIgnoreCase(white)){
                                        flag = true;
                                    }
                                }else{
                                    if (url.equalsIgnoreCase(access)){
                                        flag = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        if(!flag){
            String ss ="logflag=\"TBSGS\" on=\"3\" serial=\""+serial+"\" userip=\"-\" accessurl=\""+access+"\" orgcode=\"-\" username=\""+username+"\" identity=\""+username+"\" accessreturn=\"Y\" reason=\"success\" tbsgip=\"-\" proxycn=\""+username+"\" terminalid=\"-\" time=\""+access_time+"\" bytes=\"0\" upbytes=\"0\" serviceid=\"3\"";
            SysLogSend.sysLog(ss);
        } else {
            json = "true";
        }
        logger.info("访问控制:"+json);
        writer.write(json);
        writer.close();
        return null;
    }



}
