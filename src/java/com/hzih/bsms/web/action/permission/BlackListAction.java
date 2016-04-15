package com.hzih.bsms.web.action.permission;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.BlackList;
import com.hzih.bsms.service.BlackListService;
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
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 下午5:46
 * To change this template use File | Settings | File Templates.
 */
public class BlackListAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(BlackListAction.class);
    private LogService logService;
    private BlackListService blackListService;
    private int start;
    private int limit;
    private BlackList blackList;

    public BlackListService getBlackListService() {
        return blackListService;
    }

    public void setBlackListService(BlackListService blackListService) {
        this.blackListService = blackListService;
    }

    public BlackList getBlackList() {
        return blackList;
    }

    public void setBlackList(BlackList blackList) {
        this.blackList = blackList;
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
        boolean flag = blackListService.add(blackList);
        if (flag) {
            logger.info("新增黑名单成功"+blackList.getUrl());
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "新增黑名单成功！"+blackList.getUrl());
            json = "{success:true}";
        } else {
            logger.info("新增黑名单失败"+blackList.getUrl());
            logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "新增黑名单失败！"+blackList.getUrl());
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
        BlackList oldBlackList = new BlackList(oldUrl);
        blackListService.delete(oldBlackList);
        boolean flag = blackListService.modify(blackList);
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
        BlackList delBlackList = new BlackList(url);
        boolean flag = blackListService.delete(delBlackList);
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
        BlackList checkUrlAddress = blackListService.checkUrl(url);
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
        PageResult pageResult = blackListService.findByPages(url,start,limit);
        if(pageResult!=null){
            List<BlackList> list = pageResult.getResults();
            int count = pageResult.getAllResultsAmount();
            if(list!=null){
                String  json= "{success:true,total:" +count + ",rows:[";
                Iterator<BlackList> blackListIterator = list.iterator();
                while (blackListIterator.hasNext()){
                    BlackList log = blackListIterator.next();
                    if(blackListIterator.hasNext()){
                        json += "{url:'" + log.getUrl() + "'" +
                              /*  ",status:"+log.getStatus()+*/
                                "},";
                    }else {
                        json += "{url:'" + log.getUrl() + "'" +
                                /*",status:"+log.getStatus()+*/
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
