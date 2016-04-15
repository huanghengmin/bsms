package com.hzih.bsms.web.action.permission;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.CaRole;
import com.hzih.bsms.service.CaRolePermissionService;
import com.hzih.bsms.service.CaRoleService;
import com.hzih.bsms.service.CaUserRoleService;
import com.hzih.bsms.service.LogService;
import com.hzih.bsms.web.SessionUtils;
import com.hzih.bsms.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午2:32
 * To change this template use File | Settings | File Templates.
 */
public class CaRoleAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(CaRoleAction.class);
    private LogService logService;
    private int start;
    private int limit;
    private CaRole caRole;
    private CaRoleService caRoleService;
    private CaUserRoleService caUserRoleService;
    private CaRolePermissionService caRolePermissionService;

    public CaRolePermissionService getCaRolePermissionService() {
        return caRolePermissionService;
    }

    public void setCaRolePermissionService(CaRolePermissionService caRolePermissionService) {
        this.caRolePermissionService = caRolePermissionService;
    }

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

    public CaRole getCaRole() {
        return caRole;
    }

    public void setCaRole(CaRole caRole) {
        this.caRole = caRole;
    }

    public CaRoleService getCaRoleService() {
        return caRoleService;
    }

    public void setCaRoleService(CaRoleService caRoleService) {
        this.caRoleService = caRoleService;
    }

    public String add() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        caRole.setCreatedTime(new Date());
        try{
            boolean flag = caRoleService.add(caRole);
            if(flag){
                json= "{success:true,msg:'新增角色信息成功'}";
                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户新增角色信息成功");
            }  else {
                json = "{success:false,msg:'新增角色失败'}";
                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户新增角色信息失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String modify()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        String id = request.getParameter("id");
        caRole.setModifiedTime(new Date());
        caRole.setId(Integer.parseInt(id));
        try{
            boolean flag = caRoleService.modify(caRole);
            if(flag){
                json= "{success:true,msg:'修改角色信息成功'}";
                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户修改角色信息成功");
            }  else {
                json = "{success:false,msg:'修改角色失败'}";
                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户修改角色信息失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String delete()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String id = request.getParameter("id");
        CaRole ca_role = new CaRole();
        ca_role.setId(Integer.parseInt(id));
        String json = "{success:false}";
        try{
            caRolePermissionService.delAllCaPermissionsByRoleId(Integer.parseInt(id));
            caUserRoleService.delAllByRoleId(Integer.parseInt(id));
            caRoleService.delete(ca_role);
            json= "{success:true,msg:'删除角色成功!'}";
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户删除角色信息成功");

        }catch (Exception e){
            json = "{success:false,msg:'删除角色失败'}";
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户删除角色信息失败");
            logger.error(e.getMessage());
        }
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
        String role_name = request.getParameter("role_name");
        PageResult pageResult =  caRoleService.findByPages(role_name,start,limit);
        if(pageResult!=null){
            List<CaRole> list = pageResult.getResults();
            int count =  pageResult.getAllResultsAmount();
            if(list!=null){
                String  json= "{success:true,total:" + count + ",rows:[";
                Iterator<CaRole> raUserIterator = list.iterator();
                while (raUserIterator.hasNext()){
                    CaRole log = raUserIterator.next();
                    if(raUserIterator.hasNext()){
                        json += "{" +
                                "id:'"+log.getId()+
                                "',name:'" + log.getName() +
                                "',description:'" + log.getDescription() +
                                "',createdTime:'" + log.getCreatedTime() +
                                "',status:'" + log.getStatus() +
                                "',modifiedTime:'" + log.getModifiedTime() +"'" +
                                "},";
                    }else {
                        json += "{" +
                                "id:'"+log.getId()+
                                "',name:'" + log.getName() +
                                "',description:'" + log.getDescription() +
                                "',createdTime:'" + log.getCreatedTime() +
                                "',status:'" + log.getStatus() +
                                "',modifiedTime:'" + log.getModifiedTime() +"'" +
                                "}";
                    }
                }
                json += "]}";
                actionBase.actionEnd(response, json, result);
            }
        }
        return null;
    }




    public String stop()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String id = request.getParameter("id");
        CaRole ca_role = new CaRole();
        ca_role.setId(Integer.parseInt(id));
        ca_role.setStatus(1);
        String json = "{success:false}";
        try{
            caRoleService.stop(ca_role);
            json= "{success:true,msg:'禁止角色访问成功!'}";
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","禁止角色访问成功");

        }catch (Exception e){
            json = "{success:false,msg:'禁止角色访问失败'}";
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "禁止角色访问失败");
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }



    public String allow()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String id = request.getParameter("id");
        CaRole ca_role = new CaRole();
        ca_role.setId(Integer.parseInt(id));
        ca_role.setStatus(0);
        String json = "{success:false}";
        try{
            caRoleService.allow(ca_role);
            json= "{success:true,msg:'允许角色访问成功!'}";
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","允许角色访问成功");

        }catch (Exception e){
            json = "{success:false,msg:'允许角色访问失败'}";
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "允许角色访问失败");
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }


}
