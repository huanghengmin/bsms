package com.hzih.bsms.web.action.bs;

import com.hzih.bsms.service.LogService;
import com.hzih.bsms.utils.Dom4jUtil;
import com.hzih.bsms.utils.StringContext;
import com.hzih.bsms.web.SessionUtils;
import com.hzih.bsms.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-1
 * Time: 上午9:44
 * To change this template use File | Settings | File Templates.
 */
public class BsConfigAction extends ActionSupport {

    private String config_path = StringContext.systemPath+"/config/bsconfig.xml";

    private LogService logService;

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public String saveConfig() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json ="{success:false}";
        String bindAddress = request.getParameter("bindAddress");
        String bindPort = request.getParameter("bindPort");
        String keystore = request.getParameter("keystore");
        String keystorePwd = request.getParameter("keystorePwd");
        String keystoreTrust = request.getParameter("keystoreTrust");
        String keystoreTrustPwd = request.getParameter("keystoreTrustPwd");
        Document doc = Dom4jUtil.getDocument(config_path);
        if(doc!=null){
            Element server = (Element)doc.selectSingleNode("/configuration/server");
            if(server!=null){
                Element   s_bindAddress = server.element("bindAddress");
                Element   s_bindPort = server.element("bindPort");
                Element   s_keystore = server.element("keystore");
                Element   s_keystorePwd = server.element("keystorePwd");
                Element   s_keystoreTrust  = server.element("keystoreTrust");
                Element   s_keystoreTrustPwd  = server.element("keystoreTrustPwd");
                s_bindAddress.setText(bindAddress);
                s_bindPort.setText(bindPort);
                s_keystore.setText(StringContext.systemPath+"/security/Device.jks");
                s_keystorePwd.setText("123qwe");
                s_keystoreTrust.setText(StringContext.systemPath+"/jre/lib/security/cacerts");
                s_keystoreTrustPwd.setText("changeit");
                Dom4jUtil.writeDocumentToFile(doc,config_path);
                logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsConfigAction","更新安全服务配置成功！");
            }
        }else {
            Document document = DocumentHelper.createDocument();
            Element configuration = document.addElement("configuration");
            Element server = configuration.addElement("server");
            Element s_bindAddress = server.addElement("bindAddress");
            Element s_bindPort = server.addElement("bindPort");
            Element s_keystore = server.addElement("keystore");
            Element s_keystorePwd = server.addElement("keystorePwd");
            Element s_keystoreTrust = server.addElement("keystoreTrust");
            Element s_keystoreTrustPwd = server.addElement("keystoreTrustPwd");
            s_bindAddress.setText(bindAddress);
            s_bindPort.setText(bindPort);
            s_keystore.setText(StringContext.systemPath+"/security/Device.jks");
            s_keystorePwd.setText("123qwe");
            s_keystoreTrust.setText(StringContext.systemPath+"/jre/lib/security/cacerts");
            s_keystoreTrustPwd.setText("changeit");
            Dom4jUtil.writeDocumentToFile(document,config_path);
            logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "BsConfigAction","更新安全服务配置成功！");
        }
        json ="{success:true}";
        actionBase.actionEnd(response,json,result);
        return null;
    }


    public String selectConfig()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        int totalCount =0;
        StringBuilder stringBuilder = new StringBuilder();
        getData(stringBuilder);
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

    private void getData(StringBuilder stringBuilder) {
        Document doc = Dom4jUtil.getDocument(config_path);
        if(doc!=null){
            Element server = (Element)doc.selectSingleNode("/configuration/server");
            String   s_bindAddress = server.element("bindAddress").getText();
            String   s_bindPort = server.element("bindPort").getText();
            String   s_keystore = server.element("keystore").getText();
            String   s_keystorePwd = server.element("keystorePwd").getText();
            String   s_keystoreTrust  = server.element("keystoreTrust").getText();
            String   s_keystoreTrustPwd  = server.element("keystoreTrustPwd").getText();
            stringBuilder.append("{");
            stringBuilder.append("bindAddress:'"+ s_bindAddress+"',");
            stringBuilder.append("bindPort:'"+ s_bindPort+"'").append(",");
            stringBuilder.append("keystore:'"+s_keystore+"',");
            stringBuilder.append("keystorePwd:'"+s_keystorePwd+"',");
            stringBuilder.append("keystoreTrust:'"+s_keystoreTrust+"',");
            stringBuilder.append("keystoreTrustPwd:'"+s_keystoreTrustPwd+"'");
            stringBuilder.append("},");
        } else {
            stringBuilder.append("{");
            stringBuilder.append("bindAddress:'',");
            stringBuilder.append("bindPort:''");
            stringBuilder.append("keystore:'',");
            stringBuilder.append("keystorePwd:'',");
            stringBuilder.append("keystoreTrust:'',");
            stringBuilder.append("keystoreTrustPwd:''");
            stringBuilder.append("},");
        }
    }
}
