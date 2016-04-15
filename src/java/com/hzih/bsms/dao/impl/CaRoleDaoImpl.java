package com.hzih.bsms.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.CaRoleDao;
import com.hzih.bsms.domain.CaRole;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午2:06
 * To change this template use File | Settings | File Templates.
 */
public class CaRoleDaoImpl extends MyDaoSupport implements CaRoleDao {
    @Override
    public boolean add(CaRole caRole) throws Exception {
        super.getHibernateTemplate().save(caRole);
        return true;
    }

    @Override
    public boolean modify(CaRole caRole) throws Exception {
        CaRole ca_role = findById(caRole.getId());
        ca_role.setName(caRole.getName());
        ca_role.setDescription(caRole.getDescription());
        if(null!=caRole.getCreatedTime())
            ca_role.setCreatedTime(caRole.getCreatedTime());
        if(null!=caRole.getModifiedTime())
            ca_role.setModifiedTime(caRole.getModifiedTime());
        super.getHibernateTemplate().update(ca_role);
        return true;
    }

    @Override
    public boolean delete(CaRole caRole) throws Exception {
        String hql="delete from CaRole where id = "+caRole.getId();
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
    public CaRole findById(int id) throws Exception {
        String hql="from CaRole where id = "+id;
        List<CaRole> caRoles  = super.getHibernateTemplate().find(hql);
        if(caRoles!=null&&caRoles.size()>0)
            return caRoles.get(0);
        else
            return null;
    }

    @Override
    public PageResult findByPages(String role_name, int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = " from CaRole s where 1=1";
        List paramsList = new ArrayList();
        if (role_name != null && role_name.length() > 0) {
            hql += " and name like ?";
            paramsList.add("%" + role_name + "%");
        }
        String countHql = "select count(*) " + hql;

        PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),
                pageIndex, limit);
        return ps;
    }

    @Override
    public void allow(CaRole ca_role) {
        String s ="update CaRole caRole set caRole.status = "+ca_role.getStatus()+" where caRole.id = "+ca_role.getId();
        Session session = super.getSession();
        session.beginTransaction();
        Query query = session.createQuery(s);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void stop(CaRole ca_role) {
        String s ="update CaRole caRole set caRole.status = "+ca_role.getStatus()+" where caRole.id = "+ca_role.getId();
        Session session = super.getSession();
        session.beginTransaction();
        Query query = session.createQuery(s);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void setEntityClass() {
        this.entityClass = CaRole.class;
    }
}
