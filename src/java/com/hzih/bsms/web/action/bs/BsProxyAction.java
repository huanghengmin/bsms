package com.hzih.bsms.web.action.bs;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-8
 * Time: 下午2:06
 * To change this template use File | Settings | File Templates.
 */
public class BsProxyAction extends ActionSupport {
    private Logger logger = Logger.getLogger(BsProxyAction.class);
    public String proxy()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        String url = request.getParameter("url");
//        logger.info("request url:"+url);
//        if(url!=null){
//           url =  url.substring(1,url.length()-1);
//        }
//        response.sendRedirect(url);
        Socket socket = new Socket("127.0.0.1",81);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("GET /ra".getBytes());
        outputStream.flush();
        return null;
    }
}
