package com.hzih.bsms.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.CaUserDao;
import com.hzih.bsms.domain.CaUser;
import com.hzih.bsms.domain.Permission;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-24
 * Time: 下午5:02
 * To change this template use File | Settings | File Templates.
 */
public class CaUserDaoImpl extends MyDaoSupport implements CaUserDao {
    @Override
    public void setEntityClass() {
        this.entityClass = Permission.class;
    }
    @Override
    public boolean sleepRaUser(CaUser caUser) throws Exception {
        boolean flag =false;
        String s ="update CaUser caUser set caUser.status = "+caUser.getStatus()+" where caUser.id = "+caUser.getId();
        Session session = super.getSession();
        try{
            session.beginTransaction();
            Query query = session.createQuery(s);
            query.executeUpdate();
            session.getTransaction().commit();
            flag = true;

        } catch (Exception e){

        } finally {
           session.close();
        }
        return flag;
    }

    @Override
    public boolean reCastRaUser(CaUser caUser) throws Exception {
        boolean flag =false;
        String s="update CaUser caUser set caUser.status= "+caUser.getStatus()+" where caUser.id="+caUser.getId();
        Session session = super.getSession();
        try{
            session.beginTransaction();
            Query query = session.createQuery(s);
            query.executeUpdate();
            session.getTransaction().commit();
            flag = true;

        } catch (Exception e){
              logger.info(e.getMessage());
        } finally {
            session.close();
        }
        return flag;
    }

    @Override
    public boolean stopRaUser(CaUser caUser) throws Exception {
        boolean flag =false;
        String s="update CaUser caUser set caUser.status= "+caUser.getStatus()+" where caUser.id="+caUser.getId();
        Session session = super.getSession();
        try{
            session.beginTransaction();
            Query query=session.createQuery(s);
            query.executeUpdate();
            session.getTransaction().commit();
            flag=true;
        } catch (Exception e){

        } finally {
            session.close();
        }
        return flag;
    }

    @Override
    public CaUser findBySerialNumber(String serialNumber) throws Exception {
        String hql="from CaUser caUser where caUser.hzihcaserialNumber ='"+serialNumber+"'";
        List<CaUser> caUsers  = super.getHibernateTemplate().find(hql);
        if(caUsers.size()>0&&caUsers!=null){
            return caUsers.get(0);
        }else {
            return null;
        }
    }

    @Override
    public boolean add(CaUser caUser) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().save(caUser);
        flag = true;
        return flag;
    }

    @Override
    public boolean updateCaStatus(CaUser caUser) throws Exception {
        boolean flag =false;
        String hql="update from CaUser causer set causer.hzihcastatus = '"+caUser.getHzihcastatus()+"' where id = "+caUser.getId();
        Session session = super.getSession();
        try{
            session.beginTransaction();
            Query query=session.createQuery(hql);
            query.executeUpdate();
            session.getTransaction().commit();
            flag=true;
        } catch (Exception e){

        } finally {
            session.close();
        }
        return flag;
    }

    @Override
    public boolean modify(CaUser caUser) throws Exception {
        boolean flag =false;
        CaUser ca_u = findById(caUser.getId());
        if(null!=caUser.getCn())
            ca_u.setCn(caUser.getCn());
        if(null!=caUser.getHzihpassword())
            ca_u.setHzihpassword(caUser.getHzihpassword());
        if(null!=caUser.getHzihid())
            ca_u.setHzihid(caUser.getHzihid());
        if(null!=caUser.getHzihphone())
            ca_u.setHzihphone(caUser.getHzihphone());
        if(null!=caUser.getHzihaddress())
            ca_u.setHzihaddress(caUser.getHzihaddress());
        if(null!=caUser.getHzihemail())
            ca_u.setHzihemail(caUser.getHzihemail());
        if(null!=caUser.getHzihjobnumber())
            ca_u.setHzihjobnumber(caUser.getHzihjobnumber());
        if(null!=caUser.getPhonenetid())
            ca_u.setPhonenetid(caUser.getPhonenetid());
        if(null!=caUser.getTerminalid())
            ca_u.setTerminalid(caUser.getTerminalid());
        if(null!=caUser.getHzihcaserialNumber())
            ca_u.setHzihcaserialNumber(caUser.getHzihcaserialNumber());
        if(null!=caUser.getHzihdn())
            ca_u.setHzihdn(caUser.getHzihdn());
        if(null!=caUser.getHzihprovince())
            ca_u.setHzihprovince(caUser.getHzihprovince());
        if(null!=caUser.getHzihcity())
            ca_u.setHzihcity(caUser.getHzihcity());
        if(null!=caUser.getHzihorganization())
            ca_u.setHzihorganization(caUser.getHzihorganization());
        if(null!=caUser.getHzihinstitutions())
            ca_u.setHzihinstitutions(caUser.getHzihinstitutions());
        if(null!=caUser.getHzihcastatus())
            ca_u.setHzihcastatus(caUser.getHzihcastatus());
        if(null!=caUser.getHzihcaserialNumber())
            ca_u.setHzihcaserialNumber(caUser.getHzihcaserialNumber());
        if(null!=caUser.getHzihparentca())
            ca_u.setHzihparentca(caUser.getHzihparentca());
        if(null!=caUser.getHzihcertificatetype())
            ca_u.setHzihcertificatetype(caUser.getHzihcertificatetype());
        if(null!=caUser.getHzihcavalidity())
            ca_u.setHzihcavalidity(caUser.getHzihcavalidity());
        if(null!=caUser.getOnlinetime())
            ca_u.setOnlinetime(caUser.getOnlinetime());
        if(null!=caUser.getLogindate())
            ca_u.setLogindate(caUser.getLogindate());
        if(caUser.getViewFlag()!=-1)
            ca_u.setViewFlag(caUser.getViewFlag());
        super.getHibernateTemplate().update(ca_u);
        flag = true;
        return flag;
    }

    @Override
    public boolean delete(CaUser caUser) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().delete(caUser);
        flag = true;
        return flag;
    }

    @Override
    public CaUser findById(int id) throws Exception {
        String hql="from CaUser where id = "+id;
        List<CaUser> caUsers  = super.getHibernateTemplate().find(hql);
        if(caUsers!=null&&caUsers.size()>0)
            return caUsers.get(0);
        else
            return null;
    }

    @Override
    public PageResult findByPages(String username,String userid,String phone,String email,String status ,String on,int start,int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = " from CaUser s where 1=1";
        List paramsList = new ArrayList();
        if (userid != null && userid.length() > 0) {
            hql += " and hzihid like ?";
            paramsList.add("%" + userid + "%");
        }
        if (username != null && username.length() > 0) {
            hql += " and cn like ?";
            paramsList.add("%" + username + "%");
        }
        if (phone != null && phone.length() > 0) {
            hql += " and hzihphone like ?";
            paramsList.add("%" + phone + "%");
        }
        if (email != null && email.length() > 0) {
            hql += " and hzihemail like ?";
            paramsList.add("%" + email + "%");
        }
        if (status != null && status.length() > 0) {
            hql += " and status=?";
            paramsList.add(Integer.parseInt(status));
        }
        if(null!=on && on.length() > 0){
            hql += " and online=?";
            paramsList.add(Integer.parseInt(on));
        }
        String countHql = "select count(*) " + hql;

        PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),
                pageIndex, limit);
        return ps;
    }

    @Override
    public CaUser checkUserName(String username) throws Exception {
        String hql = new String("from CaUser where cn = ?");
        List<CaUser> list = getHibernateTemplate().find(hql,new String[] { username });
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public CaUser findByCn(String cn) throws Exception {
        String hql = new String("from CaUser where cn = ?");
        List<CaUser> list = getHibernateTemplate().find(hql,new String[] { cn });
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public CaUser findByHost(String host) {
        String hql = new String("from CaUser where vpn_ip = ?");
        List<CaUser> list = getHibernateTemplate().find(hql,new String[] { host });
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean update(CaUser caUser)throws Exception{
        boolean flag =false;
        super.getHibernateTemplate().saveOrUpdate(caUser);
        flag = true;
        return flag;
    }

}
