package com.hzih.bsms.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.BlackListDao;
import com.hzih.bsms.domain.BlackList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 下午5:12
 * To change this template use File | Settings | File Templates.
 */
public class BlackListDaoImpl extends MyDaoSupport implements BlackListDao {
    @Override
    public void setEntityClass() {
        this.entityClass = BlackList.class;
    }
    @Override
    public List<BlackList> findAll() {
        String hql="from BlackList";
        List<BlackList> blackListList = null;
        try {
            blackListList = super.getHibernateTemplate().find(hql);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return blackListList;
    }

    @Override
    public BlackList checkUrl(String url) throws Exception {
        String hql="from BlackList blackList where blackList.url ='"+url+"'";
        List<BlackList> blackListList = null;
        try {
            blackListList = super.getHibernateTemplate().find(hql);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        if(blackListList!=null&&blackListList.size()>0){
            return blackListList.get(0);
        }   else {
            return null;
        }
    }

    @Override
    public boolean add(BlackList blackList) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().save(blackList);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean modify(BlackList blackList) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().saveOrUpdate(blackList);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(BlackList blackList) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().delete(blackList);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public PageResult findByPages(String url, int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = " from BlackList s where 1=1";
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
}
