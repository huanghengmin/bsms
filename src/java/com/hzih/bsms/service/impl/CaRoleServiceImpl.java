package com.hzih.bsms.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.CaRoleDao;
import com.hzih.bsms.domain.CaRole;
import com.hzih.bsms.service.CaRoleService;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
public class CaRoleServiceImpl implements CaRoleService{
    private CaRoleDao caRoleDao;

    public CaRoleDao getCaRoleDao() {
        return caRoleDao;
    }

    public void setCaRoleDao(CaRoleDao caRoleDao) {
        this.caRoleDao = caRoleDao;
    }

    @Override
    public boolean add(CaRole caRole) throws Exception {
        return caRoleDao.add(caRole);
    }

    @Override
    public boolean modify(CaRole caRole) throws Exception {
        return caRoleDao.modify(caRole);
    }

    @Override
    public boolean delete(CaRole caRole) throws Exception {
        return caRoleDao.delete(caRole);
    }

    @Override
    public CaRole findById(int id) throws Exception {
        return caRoleDao.findById(id);
    }

    @Override
    public PageResult findByPages(String role_name, int start, int limit) throws Exception {
        return caRoleDao.findByPages(role_name,start,limit);
    }

    @Override
    public void allow(CaRole ca_role) {
        caRoleDao.allow(ca_role);
    }

    @Override
    public void stop(CaRole ca_role) {
        caRoleDao.stop(ca_role);
    }
}
