package com.hzih.bsms.web.action.permission;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.CaUser;
import com.hzih.bsms.service.CaUserRoleService;
import com.hzih.bsms.service.CaUserService;
import com.hzih.bsms.service.LogService;
//import com.hzih.bsms.utils.CallCaRequests;
import com.hzih.bsms.utils.FileUtil;
import com.hzih.bsms.utils.ServiceResponse;
import com.hzih.bsms.utils.StringContext;
import com.hzih.bsms.web.SessionUtils;
import com.hzih.bsms.web.action.ActionBase;
//import com.hzih.bsms.web.action.ca.CaConfigXml;
//import com.hzih.bsms.web.action.mc.servlet.CallVPNStatus;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-24
 * Time: 下午4:53
 * To change this template use File | Settings | File Templates.
 */
public class CaUserAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(CaUserAction.class);
    private LogService logService;
    private CaUserService caUserService;
    private int start;
    private int limit;
    private CaUser caUser;
    private CaUserRoleService caUserRoleService;

    public CaUserRoleService getCaUserRoleService() {
        return caUserRoleService;
    }

    public void setCaUserRoleService(CaUserRoleService caUserRoleService) {
        this.caUserRoleService = caUserRoleService;
    }

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public CaUserService getCaUserService() {
        return caUserService;
    }

    public void setCaUserService(CaUserService caUserService) {
        this.caUserService = caUserService;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public CaUser getCaUser() {
        return caUser;
    }

    public void setCaUser(CaUser caUser) {
        this.caUser = caUser;
    }

    public String sleepRaUser() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String id = request.getParameter("id");
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        CaUser delCaUser = new CaUser(Integer.parseInt(id));
        delCaUser.setStatus(1);
//        logger.info(delCaUser.toString());
        boolean flag = caUserService.sleepRaUser(delCaUser);
        if (flag) {
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "CaUser", "临时阻断成功！");
            CaUser caUser1 =caUserService.findById(Integer.parseInt(id));
//            String[][] vpn_params = new String[][] {
//                    {"username",caUser1.getCn()},
//                    {"serial",caUser1.getHzihcaserialNumber()}
//            };
//            String response_string =  CallVPNStatus.disable(vpn_params);
//            if(null!=response_string&&response_string.contains("true")){
//                json = "{success:true}";
//                logger.info("临时阻断成功");
//            }
        } else {
            logger.info("临时阻断失败");
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "CaUser", "临时阻断失败！");
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String reCastRaUser() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String id = request.getParameter("id");
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        CaUser delCaUser = new CaUser(Integer.parseInt(id));
        delCaUser.setStatus(0);
//        logger.info(delCaUser.toString());
        boolean flag = caUserService.reCastRaUser(delCaUser);
        if (flag) {
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "CaUser", "恢复接入成功！");
       /*     CaUser caUser1 =caUserService.findById(Integer.parseInt(id));
            String[][] vpn_params = new String[][] {
                    {"username",caUser1.getCn()},
                    {"serial",caUser1.getHzihcaserialNumber()}
            };
            String response_string =  CallVPNStatus.enable(vpn_params);
            if(null!=response_string&&response_string.contains("true")){
                logger.info("恢复接入成功");
                json = "{success:true}";
            }*/
//            json = "{success:true}";
        } else {
            logger.info("恢复接入失败");
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "CaUser", "恢复接入失败！");
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String stopRaUser() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String id = request.getParameter("id");
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        CaUser delCaUser = new CaUser(Integer.parseInt(id));
        delCaUser.setStatus(2);
//        logger.info(delCaUser.toString());
        boolean flag = caUserService.stopRaUser(delCaUser);
        if (flag) {

            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "CaUser", "长久阻断成功！");
           /* CaUser caUser1 =caUserService.findById(Integer.parseInt(id));
            String[][] vpn_params = new String[][] {
                    {"username",caUser1.getCn()},
                    {"serial",caUser1.getHzihcaserialNumber()}
            };
            String response_string =  CallVPNStatus.disable(vpn_params);
            if(null!=response_string&&response_string.contains("true")){
                logger.info("长久阻断成功");

                json = "{success:true}";
            }*/
        } else {
            logger.info("长久阻断失败");
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "CaUser", "长久阻断失败！");
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String add() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        boolean flag = caUserService.add(caUser);
        if (flag) {
            logger.info("新增记录成功");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "CaUser", "新增记录成功！");
            json = "{success:true}";
        } else {
            logger.info("新增记录失败");
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "CaUser", "新增记录失败！");
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String modify() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        String id =  request.getParameter("id");
        caUser.setId(Integer.parseInt(id));
        boolean flag = caUserService.modify(caUser);
        if (flag) {
            logger.info("更新记录成功");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "CaUser", "更新记录成功！" );
            json = "{success:true}";
        } else {
            logger.info("更新记录失败");
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "CaUser", "更新记录失败！" );
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String delete() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String id = request.getParameter("id");
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        CaUser delRaUser = new CaUser(Integer.parseInt(id));
        try{
            caUserRoleService.delByUserId(Integer.parseInt(id));
            boolean flag = caUserService.delete(delRaUser);
            if (flag) {
                logger.info("删除记录成功");
                logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "CaUser", "删除记录成功！");
                json = "{success:true}";
            } else {
                logger.info("删除记录失败");
                logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "CaUser", "删除记录失败！" );
            }
        }catch (Exception e){
            logger.info("删除记录失败");
            logService.newLog("ERROR", SessionUtils.getAccount(request).getUserName(), "CaUser", "删除记录失败！" );
            logger.error(e.getMessage());
        }

        actionBase.actionEnd(response, json, result);
        return null;
    }

    /**
     * 根据ID查找
     * @return
     * @throws Exception
     */
    public String findById() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        CaUser log = caUserService.findById(caUser.getId());
        String json = "{success:true,total:" + 1 + ",rows:[";
        if(log!=null){
            json += "{" +
                    "id:'"+ checkValue(log.getId())+
                    "',cn:'" +  checkValue(log.getCn()) +
                    "',hzihpassword:'" +  checkValue(log.getHzihpassword()) +
                    "',hzihid:'" +  checkValue(log.getHzihid()) +
                    "',hzihphone:'" +  checkValue(log.getHzihphone()) +
                    "',hzihaddress:'" +  checkValue(log.getHzihaddress()) +
                    "',hzihemail:'" +  checkValue(log.getHzihemail()) +
                    "',hzihjobnumber:'" +  checkValue(log.getHzihjobnumber()) +
                    "',phonenetid:'" +  checkValue(log.getPhonenetid()) +
                    "',hzihcaserialNumber:'" +  checkValue(log.getHzihcaserialNumber()) +
                    "',terminalid:'" +  checkValue(log.getTerminalid()) +
//                    "',terminal_pwd:'" +  checkValue(log.getTerminal_pwd()) +
//                    "',terminal_pwd_audit:'" +  checkValue(log.getTerminal_pwd_audit()) +
                    "',hzihdn:'" +  checkValue(log.getHzihdn()) +
                    "',hzihprovince:'" +  checkValue(log.getHzihprovince()) +
                    "',hzihcity:'" +  checkValue(log.getHzihcity()) +
                    "',hzihorganization:'" +  checkValue(log.getHzihorganization()) +
                    "',hzihinstitutions:'" +  checkValue(log.getHzihinstitutions()) +
                    "',hzihcastatus:'" +  checkValue(log.getHzihcastatus()) +
                    "',hzihparentca:'" +  checkValue(log.getHzihparentca()) +
                    "',hzihcavalidity:'" +  checkValue(log.getHzihcavalidity()) +
                    "',status:'" +  checkValue(log.getStatus()) +
                    "',hzihcertificatetype:'" + checkValue(log.getHzihcertificatetype()) + "'" +
                    "}";
        }
        json += "]}";
        actionBase.actionEnd(response, json, result);
        return null;
    }

    /**
     * 查找
     * @return
     * @throws Exception
     */
    public String findByPages() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String username = request.getParameter("username");
        String userid = request.getParameter("userid");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String status = request.getParameter("status");
        PageResult pageResult =  caUserService.findByPages(username,userid,phone,email,status,null,start,limit);
        if(pageResult!=null){
        List<CaUser> list = pageResult.getResults();
        int count =  pageResult.getAllResultsAmount();
        if(list!=null){
            String  json= "{success:true,total:" + count + ",rows:[";
            Iterator<CaUser> raUserIterator = list.iterator();
            while (raUserIterator.hasNext()){
                CaUser log = raUserIterator.next();
                if(raUserIterator.hasNext()){
                    json += "{" +
                            "id:'"+ checkValue(log.getId())+
                            "',cn:'" +  checkValue(log.getCn()) +
                            "',hzihpassword:'" +  checkValue(log.getHzihpassword()) +
                            "',hzihid:'" +  checkValue(log.getHzihid()) +
                            "',hzihphone:'" +  checkValue(log.getHzihphone()) +
                            "',hzihaddress:'" +  checkValue(log.getHzihaddress()) +
                            "',hzihemail:'" +  checkValue(log.getHzihemail()) +
                            "',hzihjobnumber:'" +  checkValue(log.getHzihjobnumber()) +
                            "',phonenetid:'" +  checkValue(log.getPhonenetid()) +
                            "',hzihcaserialNumber:'" +  checkValue(log.getHzihcaserialNumber()) +
                            "',terminalid:'" +  checkValue(log.getTerminalid()) +
//                            "',terminal_pwd:'" +  checkValue(log.getTerminal_pwd()) +
//                            "',terminal_pwd_audit:'" +  checkValue(log.getTerminal_pwd_audit()) +
                            "',hzihdn:'" +  checkValue(log.getHzihdn()) +
                            "',hzihprovince:'" +  checkValue(log.getHzihprovince()) +
                            "',hzihcity:'" +  checkValue(log.getHzihcity()) +
                            "',hzihorganization:'" +  checkValue(log.getHzihorganization()) +
                            "',hzihinstitutions:'" +  checkValue(log.getHzihinstitutions()) +
                            "',hzihcastatus:'" +  checkValue(log.getHzihcastatus()) +
                            "',hzihparentca:'" +  checkValue(log.getHzihparentca()) +
                            "',hzihcavalidity:'" +  checkValue(log.getHzihcavalidity()) +
                            "',status:'" +  checkValue(log.getStatus()) +
                            "',hzihcertificatetype:'" + checkValue(log.getHzihcertificatetype()) + "'" +
                            "},";
                }else {
                    json += "{" +
                            "id:'"+ checkValue(log.getId())+
                            "',cn:'" +  checkValue(log.getCn()) +
                            "',hzihpassword:'" +  checkValue(log.getHzihpassword()) +
                            "',hzihid:'" +  checkValue(log.getHzihid()) +
                            "',hzihphone:'" +  checkValue(log.getHzihphone()) +
                            "',hzihaddress:'" +  checkValue(log.getHzihaddress()) +
                            "',hzihemail:'" +  checkValue(log.getHzihemail()) +
                            "',hzihjobnumber:'" +  checkValue(log.getHzihjobnumber()) +
                            "',phonenetid:'" +  checkValue(log.getPhonenetid()) +
                            "',hzihcaserialNumber:'" +  checkValue(log.getHzihcaserialNumber()) +
                            "',terminalid:'" +  checkValue(log.getTerminalid()) +
//                            "',terminal_pwd:'" +  checkValue(log.getTerminal_pwd()) +
//                            "',terminal_pwd_audit:'" +  checkValue(log.getTerminal_pwd_audit()) +
                            "',hzihdn:'" +  checkValue(log.getHzihdn()) +
                            "',hzihprovince:'" +  checkValue(log.getHzihprovince()) +
                            "',hzihcity:'" +  checkValue(log.getHzihcity()) +
                            "',hzihorganization:'" +  checkValue(log.getHzihorganization()) +
                            "',hzihinstitutions:'" +  checkValue(log.getHzihinstitutions()) +
                            "',hzihcastatus:'" +  checkValue(log.getHzihcastatus()) +
                            "',hzihparentca:'" +  checkValue(log.getHzihparentca()) +
                            "',hzihcavalidity:'" +  checkValue(log.getHzihcavalidity()) +
                            "',status:'" +  checkValue(log.getStatus()) +
                            "',hzihcertificatetype:'" + checkValue(log.getHzihcertificatetype()) + "'" +
                            "}";
                }
            }
            json += "]}";
            actionBase.actionEnd(response, json, result);
            }
        }
        return null;
    }

    //检查数据是否为空
    public String checkValue(Object value){
        if(value==null){
            return "";
        }
        return value.toString();
    }

    /**
     * 发布证书
     * @return
     * @throws Exception
     */
    public String certificateIssued() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json =null;
        String DN = request.getParameter("DN");
        String CN = request.getParameter("CN");
        String validate = request.getParameter("validate");
        String province = request.getParameter("hzihprovince");
        String city = request.getParameter("hzihcity");
        String hzihid = request.getParameter("hzihid");
        String organization = request.getParameter("hzihorganization");
        String institutions = request.getParameter("hzihinstitutions");
        String password = request.getParameter("password");
//        String keyLength = request.getParameter("keyLength");
        String type = request.getParameter("type");
       /* String[][] params = new String[][] {
                {"DN",DN},
                {"CN",CN},
                {"validate",validate},
                {"province",province},
                {"city",city},
                {"hzihid",hzihid},
                {"organization",organization},
                {"institutions",institutions},
                {"password",password},
//                {"keyLength",keyLength},
                {"type",type}
        };
        ServiceResponse serviceResponse = CallCaRequests.calReleaseUserService(params,
                CaConfigXml.getAttribute(CaConfigXml.ca_ip),
                CaConfigXml.getAttribute(CaConfigXml.ca_port));
        String serviceResponseData = serviceResponse.getData();
        if(serviceResponseData.startsWith("{success:true}")){
            logger.info("发布证书成功!"+DN);
            CaUser caUser1 = caUserService.findByCn(CN);
            if(null!=caUser1){
                caUser1.setHzihcastatus("3");
               boolean flag = caUserService.updateCaStatus(caUser1);
                if(flag){
                    json = serviceResponseData;
                }
            }
        }   else {
            json = serviceResponseData;
        }*/
        writer.write(json);
        return null;
    }

    /**
     * 吊销
     * @return
     * @throws Exception
     */
    public String revokeCa() throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json ="{success:false}";
        String DN = request.getParameter("DN");
        String CN = request.getParameter("CN");
     /*   String[][] params = new String[][] {
                {"DN",DN} ,
                {"CN",CN}
        };
        ServiceResponse serviceResponse = CallCaRequests.calRevokeUserService(params,
                CaConfigXml.getAttribute(CaConfigXml.ca_ip),
                CaConfigXml.getAttribute(CaConfigXml.ca_port));
        String serviceResponseData = serviceResponse.getData();
        if(serviceResponseData.startsWith("{success:true}")){
            logger.info("吊销证书成功"+DN);
            json = serviceResponseData;
            CaUser caUser1 = caUserService.findByCn(CN);
            if(null!=caUser1){
                caUser1.setHzihcastatus("4");
                boolean flag = caUserService.updateCaStatus(caUser1);
                if(flag){
                    String[][] vpn_params = new String[][] {
                            {"username",caUser1.getCn()},
                            {"serial",caUser1.getHzihcaserialNumber()}
                    };
                    String response_string =  CallVPNStatus.disable(vpn_params);
                    if(null!=response_string&&response_string.contains("true")){
//                        logger.info("长久阻断成功");
                        json = serviceResponseData;
                    }
                }
            }
        }   else {
            json = serviceResponseData;
        }*/
        writer.write(json);
        return null;
    }
    
    

    public String downOffLineModel()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json = "{success:false}";
        String Agent = request.getHeader("User-Agent");
        StringTokenizer st = new StringTokenizer(Agent,";");
        st.nextToken();
        /*得到用户的浏览器名  MS IE  Firefox*/
        String userBrowser = st.nextToken();
        File file = new File(StringContext.systemPath+"/model/ImportOffLineUsers.xls");
        if(file.exists()){
            FileUtil.downType(response, file.getName(), userBrowser);
            response = FileUtil.copy(file, response);
            json = "{success:true}";
        }else {
            logger.info("下载批量导入用户模板文件失败，文件不存在!");
        }
        actionBase.actionEnd(response,json,result);
        return null;
    }

}
