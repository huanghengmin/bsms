package com.hzih.bsms.web.action.permission;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.WhiteList;
import com.hzih.bsms.service.LogService;
import com.hzih.bsms.service.WhiteListService;
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
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 下午5:46
 * To change this template use File | Settings | File Templates.
 */
public class WhiteListAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(WhiteListAction.class);
    private LogService logService;
    private WhiteListService whiteListService;
    private int start;
    private int limit;
    private WhiteList whiteList;

    public WhiteListService getWhiteListService() {
        return whiteListService;
    }

    public void setWhiteListService(WhiteListService whiteListService) {
        this.whiteListService = whiteListService;
    }

    public WhiteList getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(WhiteList whiteList) {
        this.whiteList = whiteList;
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

    public String add() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        boolean flag = whiteListService.add(whiteList);
        if (flag) {
            logger.info("新增白名单成功"+whiteList.getUrl());
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "新增白名单成功！"+whiteList.getUrl());
            json = "{success:true}";
        } else {
            logger.info("新增白名单失败"+whiteList.getUrl());
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "新增白名单失败！"+whiteList.getUrl());
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
        String oldUrl = request.getParameter("oldUrl");
        WhiteList oldWhiteList = new WhiteList(oldUrl);
        whiteListService.delete(oldWhiteList);
        boolean flag = whiteListService.modify(whiteList);
        if (flag) {
            logger.info("更新记录成功");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "更新记录成功！" );
            json = "{success:true}";
        } else {
            logger.info("更新记录失败");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "更新记录失败！" );
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String delete() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String url = request.getParameter("url");
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        WhiteList delWhiteList = new WhiteList(url);
        boolean flag = whiteListService.delete(delWhiteList);
        if (flag) {
            logger.info("删除记录成功");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "删除记录成功！");
            json = "{success:true}";
        } else {
            logger.info("删除记录失败");
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "删除记录失败！" );
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String checkUrl() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String url = request.getParameter("url");
        String json = "{success:true,msg:'true'}";
        WhiteList checkUrlAddress = whiteListService.checkUrl(url);
        if(checkUrlAddress!=null){
            json = "{success:true,msg:'false'}";
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String findByPages() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String url = request.getParameter("url");
        PageResult pageResult = whiteListService.findByPages(url,start,limit);
        if(pageResult!=null){
            List<WhiteList> list = pageResult.getResults();
            int count = pageResult.getAllResultsAmount();
            if(list!=null){
                String  json= "{success:true,total:" +count + ",rows:[";
                Iterator<WhiteList> whiteListIterator = list.iterator();
                while (whiteListIterator.hasNext()){
                    WhiteList log = whiteListIterator.next();
                    if(whiteListIterator.hasNext()){
                        json += "{url:'" + log.getUrl() + "'" +
                            /*    ",status:"+log.getStatus()+"" +*/
                                "},";
                    }else {
                        json += "{url:'" + log.getUrl() + "'" +
                             /*   ",status:"+log.getStatus()+*/
                                "}";
                    }
                }
                json += "]}";
                actionBase.actionEnd(response, json, result);
            }
        }
        return null;
    }
}
