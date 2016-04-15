package com.hzih.bsms.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.HttpDao;
import com.hzih.bsms.dao.SiteDao;
import com.hzih.bsms.domain.Http;
import com.hzih.bsms.domain.Https;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-22
 * Time: 下午2:58
 * To change this template use File | Settings | File Templates.
 */
public class HttpDaoImpl extends MyDaoSupport implements HttpDao{

    @Override
    public boolean add(Http site) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().save(site);
        flag = true;
        return flag;
    }

    @Override
    public boolean modify(Http site) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().update(site);
        flag = true;
        return flag;
    }

    @Override
    public boolean delete(Http site) throws Exception {
        boolean flag =false;
        super.getHibernateTemplate().delete(site);
        flag = true;
        return flag;
    }

    @Override
    public PageResult findByPages(int start, int limit) throws Exception {
        int pageIndex = start/limit+1;
        String hql = "from Http";
        String countHql = "select count(*) " + hql;
        PageResult ps = this.findByPage(hql, countHql, pageIndex, limit);
        return ps;
    }

    @Override
    public void setEntityClass() {
        this.entityClass = Http.class;
    }
}
