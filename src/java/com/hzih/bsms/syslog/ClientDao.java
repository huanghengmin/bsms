package com.hzih.bsms.syslog;

import com.hzih.bsms.domain.CaUser;
import com.hzih.bsms.syslog.format.OpenVpnLog;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-12-25
 * Time: 下午6:11
 * To change this template use File | Settings | File Templates.
 */
public class ClientDao extends BaseHibernateDAO {
    private Logger logger = Logger.getLogger(ClientDao.class);

    public CaUser findBySerialNumber(String serialNumber) throws Exception {
        String hql=" from CaUser caUser where caUser.hzihcaserialNumber ='"+serialNumber+"'";
        Session session = getSession();
        ArrayList<CaUser> list = (ArrayList<CaUser>) session.createQuery(hql).list();
        session.close();
        if(null!=list&&list.size()>0){
            return list.get(0);
        }else {
            return null;
        }
    }


    public boolean modify(OpenVpnLog log) throws Exception {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date online_date = null;
        if(null!=log.getTime())
        online_date = format.parse(log.getTime());
        String hql=" from CaUser caUser where caUser.cn ='"+log.getUsername()+"'";
        Session session = getSession();
        ArrayList<CaUser> list = (ArrayList<CaUser>) session.createQuery(hql).list();
        CaUser temp = null;
        if(null!=list&&list.size()>0){
            temp = list.get(0);
            if(null!=temp){
                int on = 0;
                    try{
                        on = Integer.parseInt(log.getOn());
                    }catch (Exception e){
                }
                temp.setOn(on);
                temp.setUser_ip(log.getUserip());
                temp.setVpn_ip(log.getVpnip());
                if(1==on){
                    if(null!=online_date){
                        temp.setLogindate(online_date);
                        temp.setOnlinetime(online_date);

                    }
                    session.getTransaction().begin();
                    session.update(temp);
                    session.getTransaction().commit();
                    session.close();
                    logger.info("更新用户:"+log.getUsername()+"上线信息成功!");
                }else {
                    if(null!=online_date)
                        temp.setOnlinetime(online_date);
                    session.getTransaction().begin();
                    session.update(temp);
                    session.getTransaction().commit();
                    session.close();
                    logger.info("更新用户:"+log.getUsername()+"下线信息成功!");

                }
                return true;
            }
        }
        return false;
    }

   /* public static void main(String args[])throws Exception{
        ClientDao clientDao = new ClientDao();
        CaUser caUser = clientDao.findBySerialNumber("9AE80CE743D3476681E4F234DE918174");
        System.out.print(caUser);
    }*/

}
