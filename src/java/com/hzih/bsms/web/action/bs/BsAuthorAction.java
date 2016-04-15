package com.hzih.bsms.web.action.bs;
import com.hzih.bsms.syslog.SysLogSend;
import com.hzih.bsms.utils.ServiceResponse;
import com.hzih.bsms.web.action.ra.ControlAccessXml;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-19
 * Time: 下午5:34
 * To change this template use File | Settings | File Templates.
 */
public class BsAuthorAction extends ActionSupport {
    private Logger logger = Logger.getLogger(BsAuthorAction.class);

    //鉴权校验
    public ServiceResponse callRaService(String[][] params,/*String ip,String port,*/String requestUrl) {
        logger.info("请求鉴权:"+requestUrl);
//        String requestUrl = "http://"+ip+":"+port+"/ra/AccessControls_author.action";
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5 * 1000);
        client.getHttpConnectionManager().getParams().setSoTimeout(5 * 1000);
        PostMethod post = new PostMethod(requestUrl);
        post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5 * 1000);
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        for (String[] param : params) {
            post.addParameter(param[0], param[1]);
        }
        ServiceResponse response = new ServiceResponse();
        int statusCode = 0;
        try {
            statusCode = client.executeMethod(post);
            response.setCode(statusCode);
            logger.info("返回状态码"+statusCode);
            if (statusCode == 200) {
                String data = post.getResponseBodyAsString();
                response.setData(data);
            }
        } catch (Exception e) {
            logger.error("获取证书有效期失败!", e);
        }
        return response;
    }

    //鉴权问 bs
   /* public String access()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
//        String CERT_HEX_SN=null; //证书序列号
//        String CERT_CN =null;   //证书Cn
//        String granularity = null;
//        String uri = null;
        String json = "false";
//        String s_user = request.getHeader("pu");
//        String g_uri = request.getHeader("pr");
//        String[] s_user_values = null ;
//        String[]  g_uri_values = null;
//
//        if(s_user.contains(";")&&s_user!=null){
//            s_user_values = s_user.split(";");
//        }
//        if(s_user_values!=null){
//            for (String value:s_user_values){
//                if(value.startsWith("CERT_HEX_SN=")){
//                    CERT_HEX_SN = value.substring(value.indexOf("=")+1,value.length());
//                }else if(value.startsWith("CERT_CN=")){
//                    CERT_CN = value.substring(value.indexOf("=")+1,value.length());
//                }
//            }
//        }
//        if(g_uri.contains(";")&&g_uri!=null){
//            g_uri_values = g_uri.split(";");
//        }
//        if(g_uri_values!=null){
//            for (String value:g_uri_values){
//                if(value.startsWith("granularity=")){
//                    granularity = value.substring(value.indexOf("=")+1,value.length());
//                }else if(value.startsWith("uri=")){
//                    uri = value.substring(value.indexOf("=")+1,value.length());
//                }
//            }
//        }
//        String username = null;
////        String user_id = null;
//        if(CERT_CN!=null){
//            if(CERT_CN.contains(" ")){
//                username = CERT_CN.substring(0,CERT_CN.lastIndexOf(" "));
////                user_id = CERT_CN.substring(CERT_CN.lastIndexOf(" ")+1,CERT_CN.length());
//            }else{
//                username = CERT_CN;
//            }
//        }
        //  Integer.toHexString(int);    // 十进制转化为十六进制。
        String CERT_HEX_SN = request.getParameter("CERT_HEX_SN");
        String username = request.getParameter("username");
        String uri = request.getParameter("uri");

        String serialNumber = CERT_HEX_SN.toUpperCase();    // 十六进制转化为十进制。
        while (serialNumber.startsWith("0")){
            serialNumber = serialNumber.substring(1,serialNumber.length());
        }

        JDBCUserOperation jdbcUserOperation = new JDBCUserOperation();
        List<Map<String,Object>> resources = jdbcUserOperation.getAllResourcesByUser(username);
        List<String> urls = jdbcUserOperation.getAllUrls(resources);
        boolean flag = false;
        if(urls.size()>0&&urls!=null){
            for (String url:urls){
                 if(uri.trim().toLowerCase().contains(url.toLowerCase())){
                     flag = true;
                 }
            }
            if(flag){
//          String[][] params = new String[][] {
//                     {"pu",s_user},
//                     {"pr",g_uri}
//              };
//              ServiceResponse serviceResponse = callRaService(params,"192.168.1.8","8080");
//              String serviceResponseData = serviceResponse.getData();
//              if(serviceResponseData.equals("true")){
//              response.setStatus(200);
                json ="true";
                logger.info("CERT_HEX_SN:"+serialNumber+",CERT_CN:"+username+"访问地址:"+uri+"BS允许访问!");
//              }
            }  else {
                logger.info("CERT_HEX_SN:"+serialNumber+",CERT_CN:"+username+"访问地址:"+uri+"BS没有对应可以访问资源!");
            }
        }else {
            logger.info("CERT_HEX_SN:"+serialNumber+",CERT_CN:"+username+"访问地址:"+uri+"BS没有对应可以访问资源!");
        }
        writer.write(json);
        writer.close();
        return null;
    }*/

     //bs 问鉴权
    public String author()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("utf-8");
        try{
        PrintWriter writer = response.getWriter();
        String CERT_HEX_SN=null; //证书序列号
        String CERT_CN =null;   //证书Cn
        String granularity = null;
        String uri = null;
        String s_user = request.getHeader("pu");
        String g_uri = request.getHeader("pr");
        String[] s_user_values = null ;
        String[]  g_uri_values = null;
        String json = "false";

        if(s_user!=null&&s_user.contains(";")&&s_user!=null){
            s_user_values = s_user.split(";");
        }
        if(s_user_values!=null){
            for (String value:s_user_values){
                if(value.startsWith("CERT_HEX_SN=")){
                    CERT_HEX_SN = value.substring(value.indexOf("=")+1,value.length());
                }else if(value.startsWith("CERT_CN=")){
                    CERT_CN = value.substring(value.indexOf("=")+1,value.length());
                }
            }
        }
        if(g_uri.contains(";")&&g_uri!=null){
            g_uri_values = g_uri.split(";");
        }
        if(g_uri_values!=null){
            for (String value:g_uri_values){
                if(value.startsWith("granularity=")){
                    granularity = value.substring(value.indexOf("=")+1,value.length());
                }else if(value.startsWith(" uri =")){
                    uri = value.substring(value.indexOf("=")+1,value.length());
                }else if(value.startsWith(" uri=tcp")){
                    uri = value.substring(value.indexOf("=")+1,value.length());
                }
            }
        }
        String username = null;
        String user_id = null;
        if(CERT_CN!=null){
            if(CERT_CN.contains(" ")){
                username = CERT_CN.substring(0,CERT_CN.lastIndexOf(" "));
                user_id = CERT_CN.substring(CERT_CN.lastIndexOf(" ")+1,CERT_CN.length());
            }else{
                username = CERT_CN;
            }
        }
//            移动应用管理系统的日志格式：
//            logflag="MAMSJ" userip="客户端IP地址" accessurl="访问的url" orgcode="证书的组织机构代码" username="用户名" identity="用户身份证" accessreturn=“Y或者N" reason="原因描述" mamsip="服务器IP地址" proxycn="代理证书信息" terminalid="终端信息" time="访问时间" bytes="下行流量" upbytes="上行流量" serviceid="服务id号"
        String serialNumber = CERT_HEX_SN.toUpperCase();    // 十六进制转化为十进制。
        while (serialNumber.startsWith("0")){
            serialNumber = serialNumber.substring(1,serialNumber.length());
        }

        String bsLogName ="MAMSJ";    
        String remoteIp = request.getRemoteAddr();
        String noFlag ="-";
        String yes ="Y";
        String no ="N";
            
        if(ControlAccessXml.getAttribute(ControlAccessXml.status).equals("true")){
            String[][] params = new String[][] {
                 {"CERT_HEX_SN",serialNumber},
                 {"CERT_CN",CERT_CN},
                 {"uri",uri},
                 {"granularity",granularity}/*,
                 {"user_id",user_id},*/
            };
          ServiceResponse serviceResponse = callRaService(params, ControlAccessXml.getAttribute(ControlAccessXml.control_url));
          String serviceResponseData = serviceResponse.getData();
             if(serviceResponseData!=null){
                  if(serviceResponseData.startsWith("true")){
                        logger.info("CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权评估鉴定通过!允许访问");
                        String syslog = "logflag=\""+bsLogName+"\" userip="+remoteIp+" accessurl="+uri+" orgcode="+noFlag+"" +
                                  " username="+username+" identity="+user_id+" accessreturn="+yes+"" +
                                  " reason="+"鉴权评估鉴定通过!允许访问"+" mamsip="+noFlag+" proxycn="+noFlag+"" +
                                  " terminalid="+noFlag+" time="+new Date()+" bytes="+noFlag+"" +
                                  " upbytes="+noFlag+"  serviceid=\"1\"";
                        SysLogSend.sysLog(syslog);
                         response.setStatus(200);
//                      } else {
//                          response.setStatus(403);
//                          logger.info("CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权评估鉴定通过!BS未有资源对应!禁止访问");
//                          writer.write("CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权评估鉴定通过!BS未有资源对应!禁止访问");
//                      }
                  } else {
                      String syslog = "logflag=\""+bsLogName+"\" userip="+remoteIp+" accessurl="+uri+" orgcode="+noFlag+"" +
                              " username="+username+" identity="+user_id+" accessreturn="+no+"" +
                              " reason="+"鉴权评估鉴定权限未通过,BS禁止访问!"+" mamsip="+noFlag+" proxycn="+noFlag+"" +
                              " terminalid="+noFlag+" time="+new Date()+" bytes="+noFlag+"" +
                              " upbytes="+noFlag+"  serviceid=\"1\"";
                      SysLogSend.sysLog(syslog);
                      response.setStatus(403);
                      logger.info("CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"鉴权评估鉴定权限未通过,BS禁止访问!");
                  }
             }else {
                 String syslog = "logflag=\""+bsLogName+"\" userip="+remoteIp+" accessurl="+uri+" orgcode="+noFlag+"" +
                         " username="+username+" identity="+user_id+" accessreturn="+no+"" +
                         " reason="+"BS鉴权否认！未访问到目标鉴权评估!"+" mamsip="+noFlag+" proxycn="+noFlag+"" +
                         " terminalid="+noFlag+" time="+new Date()+" bytes="+noFlag+"" +
                         " upbytes="+noFlag+"  serviceid=\"1\"";
                 SysLogSend.sysLog(syslog);
                 response.setStatus(403);
                 logger.info("CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"BS鉴权否认！未访问到目标鉴权评估!");
             }
        }else {
//            JDBCUserOperation jdbcUserOperation = new JDBCUserOperation();
//            List<Map<String,Object>> resources = jdbcUserOperation.getAllResourcesByUser(username);
//            List<String> urls = jdbcUserOperation.getAllUrls(resources);
//            boolean flag = false;
//            if(urls.size()>0&&urls!=null){
//                for (String url:urls){
//                    if(uri.trim().toLowerCase().contains(url.toLowerCase())){
//                        flag = true;
//                    }
//                }
//            }
//            if(flag){
            String syslog = "logflag=\""+bsLogName+"\" userip="+remoteIp+" accessurl="+uri+" orgcode="+noFlag+"" +
                    " username="+username+" identity="+user_id+" accessreturn="+yes+"" +
                    " reason="+"BS未启用访问控制！允许访问"+" mamsip="+noFlag+" proxycn="+noFlag+"" +
                    " terminalid="+noFlag+" time="+new Date()+" bytes="+noFlag+"" +
                    " upbytes="+noFlag+" serviceid=\"1\"";
            SysLogSend.sysLog(syslog);
                logger.info("CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"BS未启用访问控制！允许访问");
                response.setStatus(200);
//            } else {
//                response.setStatus(403);
//                logger.info("CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"取消鉴权访问,BS禁止访问！");
//                writer.write("CERT_HEX_SN:"+serialNumber+",CERT_CN:"+CERT_CN+"访问地址:"+uri+"取消鉴权访问,BS禁止访问！");
//            }
         }
        }catch (Exception e){
            logger.info("没有请求证书信息,访问出错!");
            response.setStatus(403);
        }
        return null;
    }
    
}
