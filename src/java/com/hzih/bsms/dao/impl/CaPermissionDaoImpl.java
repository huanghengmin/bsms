package com.hzih.bsms.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.CaPermissionDao;
import com.hzih.bsms.domain.CaPermission;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class CaPermissionDaoImpl extends MyDaoSupport implements CaPermissionDao {

    @Override
    public CaPermission checkUrl(String url) {
        String hql="from CaPermission where url = "+url;
        List<CaPermission> caPermissions  = super.getHibernateTemplate().find(hql);
        if(caPermissions!=null&&caPermissions.size()>0)
            return caPermissions.get(0);
        else
            return null;
    }

    @Override
    public boolean add(CaPermission caPermission) throws Exception {
        super.getHibernateTemplate().save(caPermission);
        return true;
    }

    @Override
    public boolean modify(CaPermission caPermission) throws Exception {
        CaPermission ca_p =  findById(caPermission.getId());
        ca_p.setUrl(caPermission.getUrl());
        super.getHibernateTemplate().update(ca_p);
        return true;
    }

    @Override
    public boolean delete(CaPermission caPermission) throws Exception {
        String hql="delete from CaPermission where id = "+caPermission.getId();
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
    public CaPermission findById(int id) throws Exception {
        String hql="from CaPermission where id = "+id;
        List<CaPermission> caPermissions  = super.getHibernateTemplate().find(hql);
        if(caPermissions!=null&&caPermissions.size()>0)
            return caPermissions.get(0);
        else
            return null;
    }

    @Override
    public PageResult findByPages(String url,int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = " from CaPermission s where 1=1";
        List paramsList = new ArrayList();
        if (url != null && url.length() > 0) {
            hql += " and url like ?";
            paramsList.add("%" + url + "%");
        }
        String countHql = "select count(*) " + hql;

        PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),
                pageIndex, limit);
        return ps;
    }

    @Override
    public void setEntityClass() {
        this.entityClass = CaPermission.class;
    }
}
