package com.hzih.bsms.web.action.bs;

import com.hzih.bsms.utils.Dom4jUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-24
 * Time: 下午4:38
 * To change this template use File | Settings | File Templates.
 */
public class BsPxConvertConfig {

    public static void convert(String config_path,String outputFilePath) throws IOException {
        Document doc = Dom4jUtil.getDocument(config_path);
        List<Element> proxy_s = doc.selectNodes("/root/proxy");
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<proxy_s.size();i++){
            Element element = proxy_s.get(i);
            String proxyIp = element.attributeValue("ip");
            String proxyPort = element.attributeValue("port");
            String proxyProtocol = element.attributeValue("protocol");
            List<Element> certs  = element.elements("cert");
            List<Element> private_keys  = element.elements("private_key");
            List<Element> pro_elements = element.elements("url");


            //加入语句
            sb.append("server {").append("\n");
            sb.append("listen       "+proxyIp+":"+proxyPort+";").append("\n");
            sb.append("charset  utf-8;").append("\n");
            sb.append("access_log  logs/access.log;").append("\n");

            if(proxyProtocol.equals("https")){
                sb.append("ssl on;\n");
                for (Element c:certs){
                    sb.append( "         ssl_certificate "+c.getText()+";\n");
                }
                sb.append("\n" );
                for (Element k:private_keys){
                    sb.append("         ssl_certificate_key "+k.getText()+";\n" );
                }
                sb.append("\n" );
//                sb.append("         #ssl_client_certificate /usr/local/nginx/ssl/ROOT.crt;              \n" );
                sb.append("\t\n" );
                sb.append( "         #ssl_verify_client on;   \n" );
                sb.append( "\t\t\t\t\n" );
                sb.append( "         #ssl_prefer_server_ciphers on;                      \n" );
                sb.append( "      \n" );
                sb.append( "         keepalive_timeout    60; ").append("\n");
            }

            for (Element u:pro_elements){
                String name = u.attributeValue("name");
                String value = u.attributeValue("value");
                sb.append("  location /"+name+"   { \n" +
                        "          proxy_pass  "+value+";\n" +
                        "          proxy_redirect    default ;\n" +
                        "          proxy_set_header   Host             $host:"+proxyPort+";\n" +
                        "          proxy_set_header X-Real-IP $remote_addr;\n" +
                        "          proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;\n" +
                        "      }").append("\n");
            }
            sb.append("}").append("\n");
        }
        File file = new File(outputFilePath) ;
        FileOutputStream out = new FileOutputStream(file);
        out.write(sb.toString().getBytes());
        out.flush();
        out.close();
    }
}
