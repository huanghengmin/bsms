package com.hzih.bsms.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.IPSourceDao;
import com.hzih.bsms.domain.IPSource;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: hhm
 * Date: 13-11-21
 * Time: 下午2:18
 * To change this template use File | Settings | File Templates.
 */
public class IPSourceDaoImpl extends MyDaoSupport implements IPSourceDao {


    @Override
    public boolean add(IPSource caPermission) throws Exception {
        super.getHibernateTemplate().save(caPermission);
        return true;
    }

    @Override
    public boolean modify(IPSource ipSource) throws Exception {
        super.getHibernateTemplate().update(ipSource);
        return true;
    }

    @Override
    public boolean delete(IPSource ipSource) throws Exception {
        String hql="delete from IPSource where id = "+ipSource.getId();
        boolean flag = false;
        Session session = super.getSession();
        try{
            session.beginTransaction();
            Query query = session.createQuery(hql);
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
    public IPSource findById(int id) throws Exception {
        String hql="from IPSource where id = "+id;
        List<IPSource> ipSourceList  = super.getHibernateTemplate().find(hql);
        if(ipSourceList!=null&&ipSourceList.size()>0)
            return ipSourceList.get(0);
        else
            return null;
    }

    @Override
    public PageResult findByPages(int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = " from IPSource s where 1=1";
        List paramsList = new ArrayList();
        String countHql = "select count(*) " + hql;

        PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),
                pageIndex, limit);
        return ps;
    }

    @Override
    public IPSource checkNet(String ip, String netMask) {
        String hql="from IPSource i where ip = "+ip+ " and net = "+netMask;
        List<IPSource> caPermissions  = super.getHibernateTemplate().find(hql);
        if(caPermissions!=null&&caPermissions.size()>0)
            return caPermissions.get(0);
        else
            return null;
    }

    @Override
    public void setEntityClass() {
        this.entityClass = IPSource.class;
    }
}
