package com.hzih.bsms.web.action.permission;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.CaPermissionDao;
import com.hzih.bsms.domain.CaPermission;
import com.hzih.bsms.service.CaRolePermissionService;
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
 * Time: 下午11:47
 * To change this template use File | Settings | File Templates.
 */
public class CaRolePermissionAction extends ActionSupport {
    private Logger logger = Logger.getLogger(CaRolePermissionAction.class);
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

    private CaRolePermissionService caRolePermissionService;

    private CaPermissionDao caPermissionDao;

    public CaPermissionDao getCaPermissionDao() {
        return caPermissionDao;
    }

    public void setCaPermissionDao(CaPermissionDao caPermissionDao) {
        this.caPermissionDao = caPermissionDao;
    }

    public CaRolePermissionService getCaRolePermissionService() {
        return caRolePermissionService;
    }

    public void setCaRolePermissionService(CaRolePermissionService caRolePermissionService) {
        this.caRolePermissionService = caRolePermissionService;
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
        String json = caRolePermissionService.getPerminssionsByRoleId(Integer.parseInt(roleId),start,limit);
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
        PageResult ps = caRolePermissionService.findCaPermissionsByOtherRoleId(Integer.parseInt(roleId),start,limit);
        if(ps!=null){
            List<CaPermission> list = ps.getResults();
            int count =  ps.getAllResultsAmount();
            if(list!=null){
                json.append("{success:true,total:" + count + ",rows:[");
                Iterator<CaPermission> rolePermissionIterator = list.iterator();
                while (rolePermissionIterator.hasNext()){
                    CaPermission log = rolePermissionIterator.next();
                    if(log!=null){
                        if(rolePermissionIterator.hasNext()){
                            json.append("{");
                            json.append("id:'").append(log.getId());
                            json.append("',url:'" ).append( log.getUrl()).append("'");
                            json.append("},");
                        }else {
                            json.append("{");
                            json.append("id:'").append(log.getId());
                            json.append("',url:'" ).append( log.getUrl()).append("'");
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
    public String addPermissionsToRoleId()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String json = "{success:false,msg:'添加失败'}";
        String result = actionBase.actionBegin(request);
        String roleId = request.getParameter("roleId");
        String[] pIds = request.getParameterValues("pIds");

        if(pIds!=null){
            for (String uId:pIds){
                try{
                    caRolePermissionService.addPermissionToRoleId(Integer.parseInt(uId),Integer.parseInt(roleId));
                    json = "{success:true,msg:'添加成功'}";
                }catch (Exception e){
                    logger.error(e.getMessage());
                }
            }
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }
    
    
    public String removeRoleIdPermisson()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String roleId = request.getParameter("roleId");
        String pId = request.getParameter("pId");
        String json = "{success:false,msg:'移除失败'}";
        try{
            caRolePermissionService.delByRoleIdAndPermissionId(Integer.parseInt(pId),Integer.parseInt(roleId));
            json = "{success:false,msg:'移除成功'}";
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }
}
