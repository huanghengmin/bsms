package com.hzih.bsms.web.action.bs;

import com.hzih.bsms.domain.BlackList;
import com.hzih.bsms.domain.WhiteList;
import com.hzih.bsms.utils.ServiceResponse;
import com.hzih.bsms.utils.StringContext;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-5
 * Time: 下午7:12
 * To change this template use File | Settings | File Templates.
 */
public class BlackWhiteUtils  {
//    private static final String path = StringContext.systemPath+"/blackwhite.conf";

    private  Logger logger = Logger.getLogger(BlackWhiteUtils.class);

    public boolean strategy(List<BlackList> blackLists,List<WhiteList> whiteLists){
       String black = StrategyAction.getAttribute("stop_url");
        StringBuilder sb = new StringBuilder();
        String json = "";
        int i= 0;
       if(black.equals("1")){//黑名单
            for (BlackList b:blackLists){
                String url = b.getUrl();
                if(url.equals("*")){
                    json = "http_access deny all \n";
                    break;
                }else if(url.startsWith("*")){
                    String regex_url = url.replace("*","");
                    sb.append("acl black_"+i+" url_regex -i "+regex_url+"$").append("\n");
                    sb.append("http_access deny black_"+i).append("\n");
                    i++;
                } else if(url.endsWith("*")){
                    String regex_url = url.replace("*","");
                    sb.append("acl black_"+i+" url_regex -i ^"+regex_url).append("\n");
                    sb.append("http_access deny  black_"+i).append("\n");
                    i++;
                } else if(url.contains("*")){
                    String regex_url = url.replace("*","([\\s\\S]*)");
                    sb.append("acl black_"+i+" url_regex -i ^"+regex_url+"$").append("\n");
                    sb.append("http_access deny  black_"+i).append("\n");
                    i++;
                }else {
                    sb.append("acl black_"+i+" url_regex -i ^"+url+"$").append("\n");
                    sb.append("http_access deny black_"+i).append("\n");
                    i++;
                }
            }
           if("".equals(json)){
               sb.append("http_access allow all").append("\n");
           }else {
               sb.append(json);
           }

       }else {
           for (WhiteList b:whiteLists){
               String url = b.getUrl();
               if(url.equals("*")){
                   json = "http_access allow all \n";
                   break;
               }else if(url.startsWith("*")){
                   String regex_url = url.replace("*","");
                   sb.append("acl white_"+i+" url_regex -i "+regex_url+"$").append("\n");
                   sb.append("http_access allow white_"+i).append("\n");
                   i++;
               } else if(url.endsWith("*")){
                   String regex_url = url.replace("*","");
                   sb.append("acl white_"+i+" url_regex -i ^"+regex_url).append("\n");
                   sb.append("http_access allow  white_"+i).append("\n");
                   i++;
               } else if(url.contains("*")){
                   String regex_url = url.replace("*","([\\s\\S]*)");
                   sb.append("acl white_"+i+" url_regex -i ^"+regex_url+"$").append("\n");
                   sb.append("http_access allow  white_"+i).append("\n");
                   i++;
               }else {
                   sb.append("acl white_"+i+" url_regex -i ^"+url+"$").append("\n");
                   sb.append("http_access allow white_"+i).append("\n");
                   i++;
               }
           }
           if("".equals(json)){
               sb.append("http_access deny all").append("\n");
           } else {
               sb.append(json);
           }
        }

        File f = new File(StringContext.acl_path);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f);
            try {
                out.write(sb.toString().getBytes());
                out.flush();
                out.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
    }

