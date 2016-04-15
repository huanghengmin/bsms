package com.hzih.bsms.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.WhiteListDao;
import com.hzih.bsms.domain.WhiteList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 下午5:11
 * To change this template use File | Settings | File Templates.
 */
public class WhiteListDaoImpl  extends MyDaoSupport implements WhiteListDao{
    @Override
    public void setEntityClass() {
        this.entityClass = WhiteList.class;
    }

    @Override
    public List<WhiteList> findAll(){
        String hql="from WhiteList";
        List<WhiteList> whiteListList = null;
        try {
            whiteListList = super.getHibernateTemplate().find(hql);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
       return whiteListList;
    }

    @Override
    public WhiteList checkUrl(String url) throws Exception {
        String hql="from WhiteList whiteList where whiteList.url ='"+url+"'";
        List<WhiteList> whiteListList = null;
        try {
            whiteListList = super.getHibernateTemplate().find(hql);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        if(whiteListList!=null&&whiteListList.size()>0){
            return whiteListList.get(0);
        }   else {
            return null;
        }
    }

    @Override
    public boolean add(WhiteList whiteList) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().save(whiteList);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean modify(WhiteList whiteList) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().saveOrUpdate(whiteList);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean delete(WhiteList whiteList) throws Exception {
        boolean flag =false;
        try {
            super.getHibernateTemplate().delete(whiteList);
            flag = true;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return flag;
    }

    @Override
    public PageResult findByPages(String url, int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = " from WhiteList s where 1=1";
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
