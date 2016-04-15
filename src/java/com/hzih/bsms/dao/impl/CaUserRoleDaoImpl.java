package com.hzih.bsms.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.CaUserRoleDao;
import com.hzih.bsms.domain.CaUserRole;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午10:21
 * To change this template use File | Settings | File Templates.
 */
public class CaUserRoleDaoImpl extends MyDaoSupport implements CaUserRoleDao {
    @Override
    public PageResult getUsersByRoleId(int roleId, int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = " from CaUserRole s where 1=1";
        List paramsList = new ArrayList();
        if (roleId > 0) {
            hql += " and ca_role_id = ?";
            paramsList.add(roleId);
        }
        String countHql = "select count(*) " + hql;
        PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),pageIndex, limit);
        return ps;
    }

    @Override
    public void addUsersToRoleId(String uIds,int roleId) throws Exception {
        if(uIds!=null){
            String[] ida = uIds.split(",");
            for(String s : ida) {
                int id = Integer.parseInt(s);
                super.getHibernateTemplate().save(new CaUserRole(id,roleId));
            }
        }
    }

    @Override
    public void addUserToRoleId(int uId, int roleId) throws Exception {
        super.getHibernateTemplate().save(new CaUserRole(uId,roleId));
    }


    @Override
    public boolean delAllByRoleId(int roleId) throws Exception {
        boolean flag = false;
        String hql="delete from CaUserRole where ca_role_id = "+roleId;
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
    public PageResult findCaUserByOtherRoleId(int roleId, final int start, final int limit) {
//        final String hql = "from CaUser u2 where u2.id not in (select u.id from CaUser u,CaUserRole r where u.id=r.ca_user_id) order by u2.id asc";
        final String hql = "from CaUser u2 where u2.id not in (select u.ca_user_id from CaUserRole u) order by u2.id asc";
        int pageIndex = start/limit+1;
        List paramsList = new ArrayList();
        String countHql = "select count(*) " + hql;
        PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),pageIndex, limit);
        return ps;
    }

    @Override
    public void delByRoleIdAndUserId(int i, int i1)throws Exception{
        super.getHibernateTemplate().delete(new CaUserRole(i1,i));
    }

    @Override
    public void delByUserId(int i) throws Exception {
        String hql="delete from CaUserRole where ca_user_id = "+i;
        Session session = super.getSession();
        session.beginTransaction();
        Query query=session.createQuery(hql);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<CaUserRole> findByUserId(int id) throws Exception {
        List<CaUserRole> caUserRoles = null;
        String hql = " from CaUserRole s where 1=1";
        List paramsList = new ArrayList();
        if (id >= 0) {
            hql += " and ca_user_id = ?";
            paramsList.add(id);
        }
        Session session = super.getSession();
        session.beginTransaction();
        Query query=session.createQuery(hql);
        caUserRoles = (List<CaUserRole>) query.list();
        session.getTransaction().commit();
        session.close();
        return caUserRoles;

    }

    @Override
    public void setEntityClass() {
        this.entityClass = CaUserRole.class;
    }


}
