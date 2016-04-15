package com.hzih.bsms.web.action.ra;

import com.hzih.bsms.domain.CaUser;
import com.hzih.bsms.entity.X509User;
import com.hzih.bsms.entity.mapper.X509UserAttributeMapper;
import com.hzih.bsms.service.CaUserService;
import com.hzih.bsms.web.action.ActionBase;
import com.hzih.bsms.web.action.ldap.LdapUtils;
import com.hzih.bsms.web.action.ldap.LdapXMLUtils;
import com.hzih.ssl.jdbc.JDBCUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-12
 * Time: 下午5:52
 * To change this template use File | Settings | File Templates.
 */
public class ImportRaUserAction extends ActionSupport {
    private Logger log = Logger.getLogger(ImportRaUserAction.class);
    private CaUserService caUserService;

    public CaUserService getCaUserService() {
        return caUserService;
    }

    public void setCaUserService(CaUserService caUserService) {
        this.caUserService = caUserService;
    }

    public String importRaUser()throws Exception{
      /*  HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result =	actionBase.actionBegin(request);
        String json = "{success:false,msg:'导入失败!'}";
        JDBCUtil jdbcUtil = new JDBCUtil();
        List<Map<String,String>> list = jdbcUtil.selectSQL("select * from ca_user");
        if(null!=list){
            for (Map<String,String> map:list){

                CaUser sql_user = caUserService.findById(Integer.parseInt(map.get("id")));
                if(null==sql_user){
                    CaUser  user = new CaUser();
                    //            user.setInstitutions(map.get("hzihinstitutions"));
                    user.setCn(map.get("cn"));
                    user.setHzihpassword(map.get("hzihpassword"));
                    user.setHzihid(map.get("hzihid"));
                    user.setHzihphone(map.get("hzihphone"));
                    user.setHzihaddress(map.get("hzihaddress"));
                    user.setHzihemail(map.get("hzihemail"));
                    user.setHzihjobnumber(map.get("hzihjobnumber"));
                    user.setHzihcaserialNumber(map.get("hzihcaserialNumber"));
                    user.setHzihdn(map.get("hzihdn"));
                    user.setHzihprovince(map.get("hzihprovince"));
                    user.setHzihcity(map.get("hzihcity"));
                    user.setHzihorganization(map.get("hzihorganization"));
                    user.setHzihinstitutions(map.get("hzihinstitutions"));
                    user.setStatus(Integer.parseInt(map.get("status")));
                    user.setOn(Integer.parseInt(map.get("online")));
                    user.setUser_ip(map.get("user_ip"));
                    user.setVpn_ip(map.get("vpn_ip"));
                    user.setId(Integer.parseInt(map.get("id")));
                    caUserService.add(user);
                }else {
                    sql_user.setCn(map.get("cn"));
                    sql_user.setHzihpassword(map.get("hzihpassword"));
                    sql_user.setHzihid(map.get("hzihid"));
                    sql_user.setHzihphone(map.get("hzihphone"));
                    sql_user.setHzihaddress(map.get("hzihaddress"));
                    sql_user.setHzihemail(map.get("hzihemail"));
                    sql_user.setHzihjobnumber(map.get("hzihjobnumber"));
                    sql_user.setHzihcaserialNumber(map.get("hzihcaserialNumber"));
                    sql_user.setHzihdn(map.get("hzihdn"));
                    sql_user.setHzihprovince(map.get("hzihprovince"));
                    sql_user.setHzihcity(map.get("hzihcity"));
                    sql_user.setHzihorganization(map.get("hzihorganization"));
                    sql_user.setHzihinstitutions(map.get("hzihinstitutions"));
                    sql_user.setStatus(Integer.parseInt(map.get("status")));
                    sql_user.setOn(Integer.parseInt(map.get("online")));
                    sql_user.setUser_ip(map.get("user_ip"));
                    sql_user.setVpn_ip(map.get("vpn_ip"));
                    caUserService.update(sql_user);
                }
            }
            json = "{success:true,msg:'导入鉴权用户成功!'}";
        }
        actionBase.actionEnd(response,json,result);
        return null;*/

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        String msg = null;
        String json = null;
        DirContext ctx = new LdapUtils().getCtx();
        if (ctx != null) {
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration en = null;
            try {
                en = ctx.search(LdapXMLUtils.getValue(LdapXMLUtils.base), "(objectClass=" + X509User.getObjAttr() + ")", constraints);
            } catch (NamingException e) {
                log.error(e.getMessage());
            }
            X509UserAttributeMapper mapper = new X509UserAttributeMapper();
            while (en != null && en.hasMoreElements()) {
                Object obj = en.nextElement();
                if (obj instanceof SearchResult) {
                    SearchResult si = (SearchResult) obj;
                    if (si != null) {
                        CaUser x509User = null;
                        try {
                            x509User = mapper.mapFromAttributes(si);
                        } catch (NamingException e) {
                            log.error(e.getMessage());
                        }
                        if (x509User != null) {
                            CaUser u = null;
                            try {
                                u = caUserService.checkUserName(x509User.getCn());
                            } catch (Exception e) {
                                log.error(e.getMessage());
                            }
                            try{
                                if (u != null) {
                                    x509User.setId(u.getId());
                                    caUserService.modify(x509User);
                                } else {
                                    caUserService.add(x509User);
                                }
                            }catch (Exception e){
                                log.error(e.getMessage());
                            }
                        }
                    }
                }
            }
            msg = "批量导入用户成功!";
        } else {
            msg = "LDAP服务器连通失败!";
        }
        LdapUtils.close(ctx);
        json = "{success:true,msg:'" + msg + "'}";
        actionBase.actionEnd(response, json, result);
        return null;
    }
}
