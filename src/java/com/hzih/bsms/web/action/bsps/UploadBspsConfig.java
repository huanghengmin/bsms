package com.hzih.bsms.web.action.bsps;

import com.hzih.bsms.utils.ServiceResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-7
 * Time: 上午10:03
 * To change this template use File | Settings | File Templates.
 */
public class UploadBspsConfig {
    private static Logger logger = Logger.getLogger(UploadBspsConfig.class);

    public static String upload_tcp_config(String requestUrl,String file_path) throws FileNotFoundException {
//        String tcp_url  = "http://"+ BsProxyConfigXml.getAttribute(BsProxyConfigXml.bs_proxy_ip)+":"+
//                "80/bsps/UploadConfigFiles_upload_tcp_config.action";
//        String http_url  = "http://"+ BsProxyConfigXml.getAttribute(BsProxyConfigXml.bs_proxy_ip)+":"+
//                "80/bsps/UploadConfigFiles_upload_http_config.action";
//        String t_http_url  = "http://"+ BsProxyConfigXml.getAttribute(BsProxyConfigXml.bs_proxy_ip)+":"+
//                "80/bsps/UploadConfigFiles_upload_tHttp_config.action";
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5 * 1000);
        client.getHttpConnectionManager().getParams().setSoTimeout(5 * 1000);
        PostMethod post = new PostMethod(requestUrl);
        post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5 * 1000);
        post.setRequestHeader("Content-Type", "application/octet-stream");
        RequestEntity requestEntity = new InputStreamRequestEntity(new FileInputStream(file_path));
        post.setRequestEntity(requestEntity);
        ServiceResponse response = new ServiceResponse();
        int statusCode = 0;
        try {
            statusCode = client.executeMethod(post);
            response.setCode(statusCode);
            logger.info("返回状态码"+statusCode);
            if (statusCode == 200) {
                String data = post.getResponseBodyAsString();
                return data;
            }
        } catch (Exception e) {
            logger.error("禁用VPN用户失败!", e);
        }
        return null;
    }

}
