package com.hzih.bsms.web.action.permission;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.CaPermission;
import com.hzih.bsms.service.CaPermissionService;
import com.hzih.bsms.service.CaRolePermissionService;
import com.hzih.bsms.service.LogService;
import com.hzih.bsms.utils.StringContext;
import com.hzih.bsms.web.SessionUtils;
import com.hzih.bsms.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午2:33
 * To change this template use File | Settings | File Templates.
 */
public class CaPermissionAction extends ActionSupport {
    private static final Logger logger = Logger.getLogger(CaPermissionAction.class);
    private LogService logService;
    private int start;
    private int limit;
    private CaPermission caPermission;
    private CaPermissionService caPermissionService;
    private CaRolePermissionService caRolePermissionService;

    public CaRolePermissionService getCaRolePermissionService() {
        return caRolePermissionService;
    }

    public void setCaRolePermissionService(CaRolePermissionService caRolePermissionService) {
        this.caRolePermissionService = caRolePermissionService;
    }

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public CaPermission getCaPermission() {
        return caPermission;
    }

    public void setCaPermission(CaPermission caPermission) {
        this.caPermission = caPermission;
    }

    public CaPermissionService getCaPermissionService() {
        return caPermissionService;
    }

    public void setCaPermissionService(CaPermissionService caPermissionService) {
        this.caPermissionService = caPermissionService;
    }


    /**
     *  生成目录
     * @param folderPath
     */
    public void newFolder(String folderPath) {
        try {
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            if (!myFilePath.exists()) {
                myFilePath.mkdir();
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    /**
     * 删除文件夹
     * @param folderPath String 文件夹路径及名称 如c:/fqf
     * @return boolean
     */
    public void delFolderFiles(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
//            java.io.File myFilePath = new java.io.File(filePath);
//            myFilePath.delete(); //删除空文件夹
        }
        catch (Exception e) {
            System.out.println("删除文件夹操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 删除文件夹
     * @param folderPath String 文件夹路径及名称 如c:/fqf
     * @return boolean
     */
    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            java.io.File myFilePath = new java.io.File(filePath);
            myFilePath.delete(); //删除空文件夹
        }
        catch (Exception e) {
            System.out.println("删除文件夹操作出错");
            e.printStackTrace();

        }

    }

    /**
     * 删除文件夹里面的所有文件
     * @param path String 文件夹路径 如 c:/fqf
     */
    public void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            }
            else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                String name = temp.getName();
                logger.info(name);
                if(!name.contains("noon.conf")){
                    temp.delete();
                }
            }
            if (temp.isDirectory()) {
                delAllFile(path+"/"+ tempList[i]);//先删除文件夹里面的文件
                delFolder(path+"/"+ tempList[i]);//再删除空文件夹
            }
        }
    }
    

    public boolean buildResource(List<CaPermission> caPermissionList) throws IOException {
            StringBuilder sb = new StringBuilder();
            for (CaPermission caPermission1 : caPermissionList){
                String url = caPermission1.getUrl();
                if(url.equals("*")){

                }else if(url.startsWith("*")){
                    String regex_url = url.replace("*","");
                    sb.append("acl sources_"+caPermission1.getId()+" url_regex -i "+regex_url+"$").append("\n");
                } else if(url.endsWith("*")){
                    String regex_url = url.replace("*","");
                    sb.append("acl sources_"+caPermission1.getId()+" url_regex -i ^"+regex_url).append("\n");
                } else if(url.contains("*")){
                    String regex_url = url.replace("*","([\\s\\S]*)");
                    sb.append("acl sources_"+caPermission1.getId()+" url_regex -i ^"+regex_url+"$").append("\n");
                } else {
                    if(!url.equals("*"))
                    sb.append("acl sources_"+caPermission1.getId()+" url_regex -i ^"+url+"$").append("\n");
                }
        }

        File f = new File(StringContext.systemPath+"/source/source.conf");
        if(f.exists()){
            f.delete();
            FileOutputStream out = new FileOutputStream(f);
            out.write(sb.toString().getBytes());
            out.flush();
            out.close();
        }   else {
            FileOutputStream out = new FileOutputStream(f);
            out.write(sb.toString().getBytes());
            out.flush();
            out.close();
        }

        String folder = StringContext.systemPath+"/acl";
        File acl_file = new File(folder);
        if(acl_file.exists()){
            delFolderFiles(folder);
        } else {
           newFolder(folder);
        }

        if(f.exists())
            return true;
        else
            return false;
    }


    public String add() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        try{
            boolean flag = caPermissionService.add(caPermission);
            if(flag){
                List<CaPermission> caPermissionList = caPermissionService.findAll();
                if(caPermissionList!=null&&caPermissionList.size()>0){
                    boolean build = buildResource(caPermissionList);
                    if(build){
                        json= "{success:true}";
                        logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户新增资源信息成功");
                    }  else {
                        json = "{success:false,msg:'新增资源失败'}";
                        logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户新增资源信息失败");
                    }
                }
            }  else {
                json = "{success:false,msg:'新增资源失败'}";
                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户新增资源信息失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String modify()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        String id = request.getParameter("id");
        caPermission.setId(Integer.parseInt(id));
        try{
            boolean flag = caPermissionService.modify(caPermission);
            if(flag){
                List<CaPermission> caPermissionList = caPermissionService.findAll();
                if(caPermissionList!=null&&caPermissionList.size()>0){
                    boolean build = buildResource(caPermissionList);
                    if(build){
                        json= "{success:true}";
                        logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户更新资源信息成功");
                    }  else {
                        json = "{success:false,msg:'更新资源失败'}";
                        logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户更新资源信息失败");
                    }
                }
            }  else {
                json = "{success:false,msg:'更新资源失败'}";
                logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户更新资源信息失败");
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public String delete()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String id = request.getParameter("id");
        String json = "{success:false}";
        CaPermission ca_per = new CaPermission();
        ca_per.setId(Integer.parseInt(id));
        try{
           caRolePermissionService.delByPermissionId(Integer.parseInt(id)) ;
            boolean flag = caPermissionService.delete(ca_per);
            if(flag){
                List<CaPermission> caPermissionList = caPermissionService.findAll();
                if(caPermissionList!=null&&caPermissionList.size()>0){
                    boolean build = buildResource(caPermissionList);
                    if(build){
                        json= "{success:true,msg:'删除资源成功'}";
                        logService.newLog("INFO",  SessionUtils.getAccount(request).getUserName(), "权限控制","用户删除资源信息成功");
                    }  else {
                        json = "{success:false,msg:'删除资源失败'}";
                        logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户删除资源信息失败");
                    }
                }
            }
        }catch (Exception e){
            json = "{success:false,msg:'删除资源失败'}";
            logService.newLog("ERROE", SessionUtils.getAccount(request).getUserName(), "权限控制", "用户删除资源信息失败");
            logger.error(e.getMessage());
        }
        actionBase.actionEnd(response, json, result);
        return null;
    }

    /**
     * 查找
     * @return
     * @throws Exception
     */
    public String findByPages() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String url = request.getParameter("url");
        PageResult pageResult =  caPermissionService.findByPages(url,start,limit);
        if(pageResult!=null){
            List<CaPermission> list = pageResult.getResults();
            int count =  pageResult.getAllResultsAmount();
            if(list!=null){
                String  json= "{success:true,total:" + count + ",rows:[";
                Iterator<CaPermission> raUserIterator = list.iterator();
                while (raUserIterator.hasNext()){
                    CaPermission log = raUserIterator.next();
                    if(raUserIterator.hasNext()){
                        json += "{" +
                                "id:'"+log.getId()+
                                "',url:'" + log.getUrl() +"'" +
                                "},";
                    }else {
                        json += "{" +
                                "id:'"+log.getId()+
                                "',url:'" + log.getUrl() + "'" +
                                "}";
                    }
                }
                json += "]}";
                actionBase.actionEnd(response, json, result);
            }
        }
        return null;
    }






    /**
     * 校验URL
     * @return
     * @throws Exception
     */
    public String checkUrl()throws Exception{
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String url = request.getParameter("url");
        String json = "{success:true,msg:'true'}";
        try{
        CaPermission ca_permission = caPermissionService.checkUrl(url);
            if(ca_permission!=null){
                json = "{success:true,msg:'false'}";
            }
        }catch (Exception e){
            json = "{success:true,msg:'true'}";
            logger.error(e.getMessage());
        }

        actionBase.actionEnd(response, json, result);
        return null;
    }

}
