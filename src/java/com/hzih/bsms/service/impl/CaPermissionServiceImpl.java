package com.hzih.bsms.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.CaPermissionDao;
import com.hzih.bsms.domain.CaPermission;
import com.hzih.bsms.service.CaPermissionService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午2:17
 * To change this template use File | Settings | File Templates.
 */
public class CaPermissionServiceImpl implements CaPermissionService{
    private CaPermissionDao caPermissionDao;

    public CaPermissionDao getCaPermissionDao() {
        return caPermissionDao;
    }

    public void setCaPermissionDao(CaPermissionDao caPermissionDao) {
        this.caPermissionDao = caPermissionDao;
    }

    @Override
    public boolean add(CaPermission caPermission) throws Exception {
        return caPermissionDao.add(caPermission);
    }

    @Override
    public boolean modify(CaPermission caPermission) throws Exception {
        return caPermissionDao.modify(caPermission);
    }

    @Override
    public boolean delete(CaPermission caPermission) throws Exception {
        return caPermissionDao.delete(caPermission);
    }

    @Override
    public CaPermission findById(int id) throws Exception {
        return caPermissionDao.findById(id);
    }

    @Override
    public PageResult findByPages(String url,int start, int limit) throws Exception {
        return caPermissionDao.findByPages(url,start,limit);
    }

    @Override
    public CaPermission checkUrl(String url) {
        return caPermissionDao.checkUrl(url);
    }

    @Override
    public List<CaPermission> findAll() {
        return caPermissionDao.findAll();
    }
}
