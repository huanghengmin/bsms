package com.hzih.bsms.web.action.permission;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.IPSource;
import com.hzih.bsms.service.IPSourceService;
import com.hzih.bsms.service.LogService;
import com.hzih.bsms.web.SessionUtils;
import com.hzih.bsms.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hhm
 * Date: 13-11-21
 * Time: 下午2:09
 * To change this template use File | Settings | File Templates.
 */
public class IPSourceAction extends ActionSupport {
    private IPSourceService ipSourceService;
    private int start;
    private int limit;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    private IPSource ipSource;

    public IPSource getIpSource() {
        return ipSource;
    }

    public void setIpSource(IPSource ipSource) {
        this.ipSource = ipSource;
    }

    public IPSourceService getIpSourceService() {
        return ipSourceService;
    }

    public void setIpSourceService(IPSourceService ipSourceService) {
        this.ipSourceService = ipSourceService;
    }

    private LogService logService;
    private Logger logger = Logger.getLogger(IPSourceAction.class);

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public String add()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        try{
            boolean flag = ipSourceService.add(ipSource);
            if(flag){
                json= "{success:true}";
                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户添加IP网段资源信息成功");
            }  else {
                json = "{success:false,msg:'添加IP网段资源失败'}";
                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户添加IP网段资源信息失败");
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

        String json = "{success:false,msg:'删除IP网段资源信息失败'}";
        try{
            boolean flag = ipSourceService.delete(ipSource);
            if(flag){
                json= "{success:true,msg:'删除IP网段资源信息成功'}";
                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户删除IP网段资源信息成功");
            }  else {
                json = "{success:false,msg:'删除IP网段资源失败'}";
                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户删除IP网段资源信息失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }


    public String find()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        try{
            PageResult pageResult =  ipSourceService.findByPages(start, limit);
            if(pageResult!=null){
                List<IPSource> list = pageResult.getResults();
                int count =  pageResult.getAllResultsAmount();
                if(list!=null){
                    String  json= "{success:true,total:" + count + ",rows:[";
                    Iterator<IPSource> raUserIterator = list.iterator();
                    while (raUserIterator.hasNext()){
                        IPSource log = raUserIterator.next();
                        if(raUserIterator.hasNext()){
                            json += "{" +
                                    "id:'"+log.getId()+
                                    "',ip:'" + log.getIp() +
                                    "',net:'" + log.getNet() +"'" +
                                    "},";
                        }else {
                            json += "{" +
                                    "id:'"+log.getId()+
                                    "',ip:'" + log.getIp() +
                                    "',net:'" + log.getNet() +"'" +
                                    "}";
                        }
                    }
                    json += "]}";
                    actionBase.actionEnd(response, json, result);
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return null;
    }

    public String update()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String id  = request.getParameter("id");
        if(null!=id){
            ipSource.setId(Integer.parseInt(id));
        }
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        try{
            boolean flag = ipSourceService.modify(ipSource);
            if(flag){
                json= "{success:true}";
                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户更新IP网段资源信息成功");
            }  else {
                json = "{success:false,msg:'更新IP网段资源失败'}";
                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户更新IP网段资源信息失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

}
