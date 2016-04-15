package com.hzih.bsms.web.action.permission;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.CaUserDao;
import com.hzih.bsms.domain.CaUser;
import com.hzih.bsms.service.CaUserRoleService;
import com.hzih.bsms.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午11:46
 * To change this template use File | Settings | File Templates.
 */
public class CaRoleUserAction extends ActionSupport {
    private Logger logger = Logger.getLogger(CaRoleUserAction.class);
    private int start;
    private int limit;

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

    private CaUserRoleService caUserRoleService;

    public CaUserRoleService getCaUserRoleService() {
        return caUserRoleService;
    }

    public void setCaUserRoleService(CaUserRoleService caUserRoleService) {
        this.caUserRoleService = caUserRoleService;
    }

    private CaUserDao caUserDao;

    public CaUserDao getCaUserDao() {
        return caUserDao;
    }

    public void setCaUserDao(CaUserDao caUserDao) {
        this.caUserDao = caUserDao;
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
        String roleId = request.getParameter("roleId");
        String json = caUserRoleService.getUsersByRoleId(Integer.parseInt(roleId),start,limit);
        actionBase.actionEnd(response, json, result);
        return null;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public String findByOtherRoleId()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String roleId = request.getParameter("roleId");
        StringBuilder json = new StringBuilder();
        PageResult ps = caUserRoleService.findCaUserByOtherRoleId(Integer.parseInt(roleId),start,limit);
        if(ps!=null){
            List<CaUser> list = ps.getResults();
            int count =  ps.getAllResultsAmount();
            if(list!=null){
                json.append("{success:true,total:" + count + ",rows:[");
                Iterator<CaUser> caUserIterator = list.iterator();
                while (caUserIterator.hasNext()){
                    CaUser log = caUserIterator.next();
                    if(log!=null){
                        if(caUserIterator.hasNext()){
                            json.append("{" );
                            json.append("id:'").append(log.getId());
                            json.append("',cn:'" ).append( log.getCn());
                            json.append("',hzihpassword:'" ).append( log.getHzihpassword() );
                            json.append("',hzihid:'" ).append( log.getHzihid());
                            json.append("',hzihphone:'").append( log.getHzihphone() );
                            json.append("',hzihaddress:'" ).append( log.getHzihaddress() );
                            json.append("',hzihemail:'").append( log.getHzihemail());
                            json.append("',hzihjobnumber:'" ).append( log.getHzihjobnumber());
                            json.append("',phonenetid:'" ).append( log.getPhonenetid());
                            json.append("',hzihcaserialNumber:'").append( log.getHzihcaserialNumber() );
                            json.append("',terminalid:'" ).append( log.getTerminalid());
//                            json.append("',terminal_pwd:'" ).append(log.getTerminal_pwd() );
//                            json.append("',terminal_pwd_audit:'" ).append( log.getTerminal_pwd_audit() );
                            json.append("',hzihdn:'" ).append( log.getHzihdn() );
                            json.append("',hzihprovince:'" ).append( log.getHzihprovince());
                            json.append("',hzihcity:'" ).append( log.getHzihcity());
                            json.append("',hzihorganization:'" ).append( log.getHzihorganization());
                            json.append("',hzihinstitutions:'" ).append( log.getHzihinstitutions() );
                            json.append("',hzihcastatus:'" ).append( log.getHzihcastatus() );
                            json.append("',hzihparentca:'" ).append( log.getHzihparentca() );
                            json.append("',hzihcavalidity:'").append( log.getHzihcavalidity());
                            json.append("',status:'" ).append(log.getStatus());
                            json.append("',hzihcertificatetype:'").append(log.getHzihcertificatetype()).append( "'" );
                            json.append("},");
                        }else {
                            json.append("{" );
                            json.append("id:'").append(log.getId());
                            json.append("',cn:'" ).append( log.getCn());
                            json.append("',hzihpassword:'" ).append( log.getHzihpassword() );
                            json.append("',hzihid:'" ).append( log.getHzihid());
                            json.append("',hzihphone:'").append( log.getHzihphone() );
                            json.append("',hzihaddress:'" ).append( log.getHzihaddress() );
                            json.append("',hzihemail:'").append( log.getHzihemail());
                            json.append("',hzihjobnumber:'" ).append( log.getHzihjobnumber());
                            json.append("',phonenetid:'" ).append( log.getPhonenetid());
                            json.append("',hzihcaserialNumber:'").append( log.getHzihcaserialNumber() );
                            json.append("',terminalid:'" ).append( log.getTerminalid());
//                            json.append("',terminal_pwd:'" ).append(log.getTerminal_pwd() );
//                            json.append("',terminal_pwd_audit:'" ).append( log.getTerminal_pwd_audit() );
                            json.append("',hzihdn:'" ).append( log.getHzihdn() );
                            json.append("',hzihprovince:'" ).append( log.getHzihprovince());
                            json.append("',hzihcity:'" ).append( log.getHzihcity());
                            json.append("',hzihorganization:'" ).append( log.getHzihorganization());
                            json.append("',hzihinstitutions:'" ).append( log.getHzihinstitutions() );
                            json.append("',hzihcastatus:'" ).append( log.getHzihcastatus() );
                            json.append("',hzihparentca:'" ).append( log.getHzihparentca() );
                            json.append("',hzihcavalidity:'").append( log.getHzihcavalidity());
                            json.append("',status:'" ).append(log.getStatus());
                            json.append("',hzihcertificatetype:'").append(log.getHzihcertificatetype()).append( "'" );
                            json.append("}");
                        }
                    }
                }
                json.append("]}");
            }
        }
        actionBase.actionEnd(response, json.toString(), result);
        return null;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public String addUserToRoleId()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false,msg:'添加失败'}";
        String roleId = request.getParameter("roleId");
        String[] uIds = request.getParameterValues("uIds");
        if(uIds!=null){
            for (String uId:uIds){
                try{
                    caUserRoleService.addUserToRoleId(Integer.parseInt(uId),Integer.parseInt(roleId));
                    json = "{success:true,msg:'添加成功'}";
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }
    
    
    public String removeRoleIdUser()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String roleId = request.getParameter("roleId");
        String userId = request.getParameter("userId");
        String json = "{success:false,msg:'移除失败'}";
        try{
            caUserRoleService.delByRoleIdAndUserId(Integer.parseInt(roleId),Integer.parseInt(userId));
            json = "{success:true,msg:'移除成功'}";
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

}
