package com.hzih.syslog;

import com.hzih.bsms.domain.CaPermission;
import com.hzih.bsms.domain.CaRole;
import com.hzih.bsms.domain.CaUser;
import com.hzih.bsms.utils.StringContext;
import com.hzih.syslog.format.OpenVpnLog;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-12-25
 * Time: 下午6:11
 * To change this template use File | Settings | File Templates.
 */
public class ClientDao extends BaseHibernateDAO {

    private Logger logger = Logger.getLogger(ClientDao.class);

    /**
     * @param log
     * @param session
     * @throws Exception
     */
    public void delAcl(OpenVpnLog log, Session session) throws Exception {
        String hql = " from CaUser caUser where caUser.vpn_ip ='" + log.getVpnip() + "'and caUser.cn !='" + log.getUsername() + "'";
        ArrayList<CaUser> list = (ArrayList<CaUser>) session.createQuery(hql).list();
        if (null != list && list.size() > 0) {
            Iterator<CaUser> iterator = list.iterator();
            if (iterator.hasNext()) {
                CaUser u = iterator.next();
                File f = new File(StringContext.systemPath + "/acl/user" + u.getId() + ".conf");
                if (f.exists()) {
                    f.delete();
                }
            }
        }
    }


    public CaUser findUser(OpenVpnLog log, Session session) throws Exception {
        String hql2 = " from CaUser caUser where caUser.cn ='" + log.getUsername() + "'";
        ArrayList<CaUser> userArrayList = (ArrayList<CaUser>) session.createQuery(hql2).list();
        if (userArrayList != null && userArrayList.size() > 0) {
            return userArrayList.get(0);
        }
        {
            return null;
        }
    }


    public boolean buildAcl(StringBuilder sb, CaUser user) throws Exception {
        File f = new File(StringContext.systemPath + "/acl/user" + user.getId() + ".conf");
        if (f.exists()) {
            f.delete();
        }
        FileOutputStream out = new FileOutputStream(f);
        out.write(sb.toString().getBytes());
        out.flush();
        out.close();
        if (f.exists()) {
            return true;
        } else {
            return false;
        }
    }


    public boolean modify(OpenVpnLog log) throws Exception {
        Session session = getSession();
        //删除acl文件
        try {
            delAcl(log, session);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        CaUser user = null;
        try {
            user = findUser(log, session);
            if (user != null) {
                Set<CaRole> caRoles = user.getCaRoles();
                if (caRoles != null && caRoles.size() > 0) {
                    CaRole caRole = caRoles.iterator().next();
                    if (caRole != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("#user:" + user.getCn() + " serial:" + log.getSerial()).append("\n");
                        sb.append("acl user" + user.getId() + " src " + log.getVpnip()).append("\n");
                        Set<CaPermission> caPermissions = caRole.getCaPermissions();
                        if (caPermissions != null && caPermissions.size() > 0) {
                            for (CaPermission caPermission : caPermissions) {
                                String url = caPermission.getUrl();
                                if (url.equals("*")) {
                                    sb.append("http_access allow user" + user.getId()).append("\n");
                                    break;
                                } else if (url.startsWith("*")) {
//                                  String regex_url = url.replace("*","");
//                                  sb.append("acl sources_"+caPermission.getId()+" url_regex -i "+regex_url+"$").append("\n");
                                    sb.append("http_access allow user" + user.getId() + " sources_" + caPermission.getId()).append("\n");

                                } else if (url.endsWith("*")) {
//                                  String regex_url = url.replace("*","");
//                                  sb.append("acl sources_"+caPermission.getId()+" url_regex -i ^"+regex_url).append("\n");
                                    sb.append("http_access allow user" + user.getId() + " sources_" + caPermission.getId()).append("\n");
                                } else if (url.contains("*")) {
//                                  String regex_url = url.replace("*","([\\s\\S]*)");
//                                  sb.append("acl sources_"+caPermission.getId()+" url_regex -i ^"+regex_url+"$").append("\n");
                                    sb.append("http_access allow user" + user.getId() + " sources_" + caPermission.getId()).append("\n");
                                } else {
                                    sb.append("http_access allow user" + user.getId() + " sources_" + caPermission.getId()).append("\n");
                                }
                            }
                        } else {
                            sb.append("http_access deny user" + user.getId()).append("\n");
                        }
                        boolean build = buildAcl(sb, user);
                        if (!build) {
                            logger.info("生成权限控制文件失败" + user.getCn());
                        }
                    } else {
                        logger.info("未给用户分配角色" + user.getCn());
                    }
                } else {
                    logger.info("未给用户分配角色" + user.getCn());
                }
            } else {
                CaUser caUser = new CaUser();
                caUser.setSerialNumber(log.getSerial());
                caUser.setIpAddress(log.getVpnip());
                session.save(caUser);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            session.close();
        }
        return false;
    }
}
