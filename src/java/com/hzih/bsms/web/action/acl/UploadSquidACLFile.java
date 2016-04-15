package com.hzih.bsms.web.action.acl;

import com.hzih.bsms.utils.FileUtil;
import com.hzih.bsms.utils.StringContext;
import com.inetec.common.util.Proc;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-7
 * Time: 上午9:36
 * To change this template use File | Settings | File Templates.
 */
public class UploadSquidACLFile extends ActionSupport {
    //管理端更新代理tcp配置
    public String upload_acl()throws Exception{
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json ="{success:false}";
        ServletInputStream inputStream = request.getInputStream();
        if(null!=inputStream) {
            FileUtil.copy(inputStream, StringContext.acl_path);
            json="{success:true}";
        }
        writer.write(json);
        writer.close();
        reload_squid();
        return null;
    }
    /* //管理端更新代理http配置
    public String upload_http_config()throws Exception{
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json ="{success:false}";
        ServletInputStream inputStream = request.getInputStream();
        if(null!=inputStream) {
            FileUtil.copy(inputStream, StringContext.nginxConfigPath+"/bsPx.conf");
            json ="{success:true}";
        }
        writer.write(json);
        writer.close();
        reload_squid();
        return null;
    }
    //管理端更新代理透明代理配置
    public String upload_tHttp_config()throws Exception{
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String json ="{success:false}";
        ServletInputStream inputStream = request.getInputStream();
        if(null!=inputStream) {
            FileUtil.copy(inputStream, StringContext.squidConfigPath+"/config.conf");
            json ="{success:true}";
        }
        writer.write(json);
        writer.close();
        reconfigure_squid();
        return null;
    }*/


    public void reload_squid(){
        Proc proc = new Proc();
//        proc.exec("sh "+StringContext.systemPath+"/bsshell/reload_squid.sh");
        proc.exec("service squid status");
        if(proc.getErrorOutput().contains("No running copy")){
            proc.exec("service squid start");
        } else {
            proc.exec("service squid reload");
        }
    }

    /*  public void reconfigure_squid(){
        Proc proc = new Proc();
        proc.exec("sh "+StringContext.systemPath+"/bsshell/reconfigure_squid.sh");
    }*/

}
