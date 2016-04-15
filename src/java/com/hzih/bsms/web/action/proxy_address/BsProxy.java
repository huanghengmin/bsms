package com.hzih.bsms.web.action.proxy_address;

import com.hzih.bsms.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: hhm
 * Date: 12-11-8
 * Time: 下午11:11
 * To change this template use File | Settings | File Templates.
 */
public class BsProxy extends ActionSupport {

    private Logger logger  = Logger.getLogger(BsProxy.class);

    public String save() throws IOException {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json ="{success:false}";
        String bs_proxy_ip = request.getParameter("bs_proxy_ip");
//        String vpn_port = request.getParameter("vpn_port");
//        String bs_ip = request.getParameter("bs_ip");
//        String bs_port = request.getParameter("bs_port");
//        if(null==vpn_port&&"".equals(vpn_port))
//            vpn_port = "80";
        BsProxyConfigXml.saveConfig(bs_proxy_ip/*,vpn_port*//*,bs_ip,bs_port*/);
        json ="{success:true}";
        actionBase.actionEnd(response,json,result);
        return null;
    }

    public String find(){
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        int totalCount =0;
        StringBuilder stringBuilder = new StringBuilder();
        try{
        getData(stringBuilder);
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
          logger.error(e.getMessage());
        }
        return null;
    }

    private void getData(StringBuilder stringBuilder) {
        stringBuilder.append("{");
        stringBuilder.append("bs_proxy_ip:'"+ BsProxyConfigXml.getAttribute(BsProxyConfigXml.bs_proxy_ip)+"'");
//        stringBuilder.append("vpn_port:'"+ BsProxyConfigXml.getAttribute(BsProxyConfigXml.vpn_port)+"'");
//        stringBuilder.append("bs_ip:'"+BsProxyConfigXml.getAttribute(BsProxyConfigXml.bs_ip)+"',");
//        stringBuilder.append("bs_port:'"+BsProxyConfigXml.getAttribute(BsProxyConfigXml.bs_port)+"'");
        stringBuilder.append("},");
    }
}
