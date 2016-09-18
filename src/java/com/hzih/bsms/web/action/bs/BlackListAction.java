package com.hzih.bsms.web.action.bs;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.BlackList;
import com.hzih.bsms.domain.WhiteList;
import com.hzih.bsms.service.BlackListService;
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
public class BlackListAction extends ActionSupport {
    private BlackWhiteUtils blackWhiteUtils = new BlackWhiteUtils();
    private static final Logger logger = Logger.getLogger(BlackListAction.class);
    private LogService logService;
    private BlackListService blackListService;
    private WhiteListService whiteListService;

    public WhiteListService getWhiteListService() {
        return whiteListService;
    }

    public void setWhiteListService(WhiteListService whiteListService) {
        this.whiteListService = whiteListService;
    }

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
            
           List<BlackList> blackLists = blackListService.findAll();
            List<WhiteList> whiteLists = whiteListService.findAll();
            boolean build = blackWhiteUtils.strategy(blackLists,whiteLists);
            if(build) {
                logger.info("新增黑名单成功"+blackList.getUrl());
                logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "新增黑名单成功！"+blackList.getUrl());
                json = "{success:true}";
            }   else {
                logger.info("新增黑名单成功"+blackList.getUrl());
                logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "新增黑名单成功！"+blackList.getUrl());
                json = "{success:true}";
            }
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
        String json = "{success:false,msg:'更新失败'}";
        String id = request.getParameter("id");
        String msg ="";
        String url = request.getParameter("oldUrl");
        if(url.equals(blackList.getUrl())) {
            msg = "修改记录与原记录相同,不允许修改!";
            json = "{success:false,msg:'"+msg+"'}";
        }else {
            blackList.setId(Integer.parseInt(id));
            boolean flag = blackListService.modify(blackList);
            if (flag) {
                List<BlackList> blackLists = blackListService.findAll();
                List<WhiteList> whiteLists = whiteListService.findAll();
                boolean build = blackWhiteUtils.strategy(blackLists,whiteLists);
                if(build) {
                    logger.info("更新记录成功");
                    logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "更新记录成功！" );
                    msg = "更新记录成功!";
                    json = "{success:true,msg:'"+msg+"'}";
                }   else {
                    logger.info("更新记录失败");
                    logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "更新记录失败！" );
                }
            } else {
                logger.info("更新记录失败");
                logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "更新记录失败！" );
            }
        }
//        BlackList oldBlackList = new BlackList(Integer.parseInt(id));
//        blackListService.delete(oldBlackList);
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
        BlackList delBlackList = new BlackList(Integer.parseInt(id));
        boolean flag = blackListService.delete(delBlackList);
        if (flag) {
            List<BlackList> blackLists = blackListService.findAll();
            List<WhiteList> whiteLists = whiteListService.findAll();
            boolean build = blackWhiteUtils.strategy(blackLists,whiteLists);
            if(build) {
                logger.info("删除记录成功");
                logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "删除记录成功！");
                json = "{success:true}";
            }   else {
                logger.info("删除记录失败");
                logService.newLog("INFO", SessionUtils.getAccount(request).getUserName(), "accessAddress", "删除记录失败！" );
            }
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
                                ",id:"+log.getId()+
                                "},";
                    }else {
                        json += "{url:'" + log.getUrl() + "'" +
                                ",id:"+log.getId()+
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
