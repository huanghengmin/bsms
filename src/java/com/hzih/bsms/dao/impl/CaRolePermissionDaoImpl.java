package com.hzih.bsms.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.CaRolePermissionDao;
import com.hzih.bsms.domain.CaRolePermission;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午10:20
 * To change this template use File | Settings | File Templates.
 */
public class CaRolePermissionDaoImpl extends MyDaoSupport implements CaRolePermissionDao{
    @Override
    public PageResult getPerminssionsByRoleId(int roleId, int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = " from CaRolePermission s where 1=1";
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
    public void addPermissionsToRoleId(String pIds,int roleId) throws Exception {
        if(pIds!=null){
            String[] ida = pIds.split(",");
            for(String s : ida) {
                int id = Integer.parseInt(s);
                super.getHibernateTemplate().save(new CaRolePermission(id,roleId));
            }
        }
    }


    @Override
    public boolean delAllCaPermissionsByRoleId(int roleId) throws Exception {
        boolean flag = false;
        String hql="delete from CaRolePermission where ca_role_id = "+roleId;
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
    public PageResult findCaPermissionsByOtherRoleId(int roleId, final int start, final int limit) {
        final String hql = "from CaPermission u2 where u2.id not in (select u.id from CaPermission u,CaRolePermission r where u.id=r.ca_permission_id and r.ca_role_id="+roleId+") order by u2.id asc";
        int pageIndex = start/limit+1;
        List paramsList = new ArrayList();
        String countHql = "select count(*) " + hql;
        PageResult ps = this.findByPage(hql, countHql, paramsList.toArray(),pageIndex, limit);
        return ps;
    }

    @Override
    public void addPermissionToRoleId(int pId, int roleId)throws Exception{
        super.getHibernateTemplate().save(new CaRolePermission(pId,roleId));
    }

    @Override
    public void delByRoleIdAndPermissionId(int i, int i1)throws Exception {
        super.getHibernateTemplate().delete(new CaRolePermission(i,i1));
    }

    @Override
    public void delByPermissionId(int i) throws Exception {
        String hql="delete from CaRolePermission where ca_permission_id = "+i;
        Session session = super.getSession();
        session.beginTransaction();
        Query query=session.createQuery(hql);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void setEntityClass() {
        this.entityClass = CaRolePermission.class;
    }
}
