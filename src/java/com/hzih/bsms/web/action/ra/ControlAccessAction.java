package com.hzih.bsms.web.action.ra;

import com.hzih.bsms.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-24
 * Time: 下午4:54
 * To change this template use File | Settings | File Templates.
 */
public class ControlAccessAction extends ActionSupport {
    private Logger logger = Logger.getLogger(ControlAccessAction.class); 
            

    public String saveConfig() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json ="{success:false}";
        String status = request.getParameter("status");
        String control_url = request.getParameter("control_url");
        ControlAccessXml.saveConfig(status,control_url);
        json ="{success:true}";
        actionBase.actionEnd(response,json,result);
        return null;
    }

    public String findConfig(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        int totalCount =0;
        StringBuilder stringBuilder = new StringBuilder();
        try{
        returnSystemConfigData(stringBuilder);
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        totalCount = totalCount+1;
        StringBuilder json=new StringBuilder("{totalCount:"+totalCount+",root:[");
        json.append(stringBuilder.toString().substring(0,stringBuilder.toString().length()-1));
        json.append("]}");
        try {
            actionBase.actionEnd(response,json.toString(),result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void returnSystemConfigData(StringBuilder stringBuilder) {
        stringBuilder.append("{");
        stringBuilder.append("status:'"+ ControlAccessXml.getAttribute(ControlAccessXml.status)+"',");
        stringBuilder.append("control_url:'"+ ControlAccessXml.getAttribute(ControlAccessXml.control_url)+"'");
        stringBuilder.append("},");
    }
}
