package com.hzih.bsms.web.action.serve;

import com.hzih.bsms.utils.StringContext;
import com.hzih.bsms.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-6
 * Time: 下午3:37
 * To change this template use File | Settings | File Templates.
 */
public class ServerParamsAction extends ActionSupport {

    private static final String email_path = StringContext.systemPath+"/server/email.conf";
    private static final String server_path = StringContext.systemPath+"/server/config.conf";
    private static final String dns_path = StringContext.systemPath+"/server/dns.conf";
    private static final String parent_path = StringContext.systemPath+"/server/parent.conf";


    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(ServerParamsAction.class);


    public boolean buildConfig(String filePath,StringBuilder sb)throws Exception{
        File file = new File(filePath);
        FileOutputStream out = new FileOutputStream(file);
        out.write(sb.toString().getBytes());
        out.flush();
        out.close();
        if(file.exists()){
            return true;
        }else {
            return false;
        }
    }


    public String updateConfig() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json = "{success:false}";
        String interface_box = request.getParameter("interface_box");
        String port = request.getParameter("port");
        String email = request.getParameter("email");
        String first_dns = request.getParameter("first_dns");
        String second_dns = request.getParameter("second_dns");
        String parent_ip = request.getParameter("parent_ip");
        String parent_port  = request.getParameter("parent_port");
        /****************************************************************************************/
        StringBuilder sb_config = new StringBuilder();
        if("all_interface".equals(interface_box)) {
            sb_config.append("http_port "+port);
        }else {
            sb_config.append("http_port "+interface_box+":"+port);
        }
        boolean  build_ser =buildConfig(server_path,sb_config);
        /****************************************************************************************/
        if(build_ser){
            if(email!=null&&!"".equals(email)){
                StringBuilder em_config = new StringBuilder();
                em_config.append("cache_mgr "+email);
                buildConfig(email_path,em_config);
            }
            /****************************************************************************************/
            if(first_dns!=null&&!"".equals(first_dns)){
                StringBuilder dn_config = new StringBuilder();
                dn_config.append("dns_nameservers "+first_dns);
                if(second_dns!=null&&!"".equals(second_dns)){
                    dn_config.append("\n");
                    dn_config.append("dns_nameservers "+second_dns);
                    buildConfig(dns_path,dn_config);
                }
            }
            if(parent_ip!=null&&!"".equals(parent_ip)){
                StringBuilder parent_config = new StringBuilder();
//                cache_peer 10.0.0.2 parent 3128 0 no-query default no-digest
                parent_config.append("cache_peer "+parent_ip);
                if(parent_port!=null&&!"".equals(parent_port)){
                    parent_config.append(" parent "+parent_port +" 0 no-query default no-digest");
                    buildConfig(parent_path,parent_config);
                }
            }
            /****************************************************************************************/
        }
        /****************************************************************************************/
        json = "{success:true}";
        actionBase.actionEnd(response, json, result);
        return null;
    }


    public String readEmail(File file )throws Exception{
        String email = "";
        if(file!=null){
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String s1 = null;
        if((s1 = br.readLine()) != null) {
            if(s1.startsWith("cache_mgr")&&s1.contains(" ")){
                email  = s1.substring(s1.indexOf(" ")+1,s1.length());
            }
        }
        br.close();
        reader.close();
        }
        return email;
    }


    public String[] readParent(File file )throws Exception{
        String[] strings = null;
        if(file!=null){
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String s1 = null;
//        cache_peer 10.0.0.2 parent 3128 0 no-query default no-digest
        if((s1 = br.readLine()) != null) {
            if(s1.startsWith("cache_peer")&&s1.contains(" ")){
                strings = new String[2];
                strings[0]="";
                String  ip_port  = s1.substring(s1.indexOf(" ")+1,s1.length());
                strings[0] = ip_port.substring(0,ip_port.indexOf(" "));
                String parent_port = ip_port.substring(ip_port.indexOf(" ")+1,ip_port.length());
                parent_port = parent_port.substring(parent_port.indexOf(" ") + 1, parent_port.length());
                strings[1] = parent_port.substring(0,parent_port.indexOf(" "));
                return strings;
            }
        }
        br.close();
        reader.close();
        }
        return strings;
    }
    
    
    public String[] readServer(File file)throws Exception{
        String[] strings = null;
        if(file!=null){
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String s1 = null;
        if((s1 = br.readLine()) != null) {
            if(s1.startsWith("http_port")&&s1.contains(" ")){
                strings = new String[2];
                strings[0]="";
                String  ip_port  = s1.substring(s1.indexOf(" ")+1,s1.length());
                if(ip_port.contains(":")){
                    String ip = ip_port.substring(0,ip_port.indexOf(":"));
                    String port = ip_port.substring(ip_port.indexOf(":")+1,ip_port.length());
                    strings[0] = ip;
                    strings[1]=port;
                } else {
                    strings[1]= ip_port;
                }
            }
        }
        br.close();
        reader.close();
        }
        return strings;
    }


    public List<String> readDns(File file)throws Exception{
        List<String> dnslist = new ArrayList<>();
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        String s1 = null;
        while ((s1 = br.readLine()) != null) {
            if(s1.startsWith("dns_nameservers")&&s1.contains(" ")){
                String  dns = s1.substring(s1.indexOf(" ")+1,s1.length());
                dnslist.add(dns);
            }
        }
        br.close();
        reader.close();
        return dnslist;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public String findConfig()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        File email_file = new File(email_path);
        String email = null;
        if(email_file.exists()){
            try {
                email = readEmail(email_file);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        File config_file = new File(server_path);
        String[] server = new String[0];
        if(config_file.exists()){
            try {
                server = readServer(config_file);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        
        File dns_file = new File(dns_path);
        List<String> dnsList = null;
        if(dns_file.exists()){
            try {
                dnsList = readDns(dns_file);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        

        File parent_file = new File(parent_path);
        String[] parnet = null;
        if(parent_file.exists()){
            parnet = readParent(parent_file);
        }
        
        
        
        StringBuilder sb= new StringBuilder("{'success':true,'totalCount':1,'root':[");
        sb.append("{");
        if(server!=null){
            if(server[0].equals("")){
                sb.append("interface:'all_interface',");
            }else {
                sb.append("interface:'"+server[0]+"',");
            }
            sb.append("port:"+server[1]+",");

            if(email!=null&&!"".equals(email))
                sb.append("email:'"+email+"',");
            else {
                sb.append("email:'',");
            }

            if(dnsList!=null&&dnsList.size()>0){
                if(dnsList.size()==1){
                    sb.append("first_dns:'"+dnsList.get(0)+"',");
                    sb.append("first_dns:''");
                } else {
                    sb.append("first_dns:'"+dnsList.get(0)+"',");
                    sb.append("second_dns:'"+dnsList.get(1)+"',");
                }
            } else {
                sb.append("first_dns:'',");
                sb.append("second_dns:'',");
            }

//            String parent_ip = request.getParameter("parent_ip");
//            String parent_port  = request.getParameter("parent_port");
             if(parnet!=null){
                if(parnet[0]!=null&&!parnet[0].equals("")){
                    sb.append("parent_ip:'"+parnet[0]+"',");
                    sb.append("parent_port:'"+parnet[1]+"'");
                }else {
                    sb.append("parent_ip:'',");
                    sb.append("parent_port:''");
                }
             }  else {
                 sb.append("parent_ip:'',");
                 sb.append("parent_port:''");
             }
        }


        sb.append("}");
        sb.append("]}");
        actionBase.actionEnd(response, sb.toString(), result);
        return null;
    }
}
