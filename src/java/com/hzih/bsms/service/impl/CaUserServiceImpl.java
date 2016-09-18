package com.hzih.bsms.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.CaUserDao;
import com.hzih.bsms.domain.CaUser;
import com.hzih.bsms.service.CaUserService;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-24
 * Time: 下午6:15
 * To change this template use File | Settings | File Templates.
 */
public class CaUserServiceImpl implements CaUserService {

    private CaUserDao caUserDao;

    public CaUserDao getCaUserDao() {
        return caUserDao;
    }

    public void setCaUserDao(CaUserDao caUserDao) {
        this.caUserDao = caUserDao;
    }

    @Override
    public CaUser findBySerialNumber(String serialNumber) throws Exception {
        return caUserDao.findBySerialNumber(serialNumber);
    }

    @Override
    public CaUser checkUserName(String username) throws Exception {
        return  caUserDao.checkUserName(username);
    }

    @Override
    public boolean add(CaUser raUser) throws Exception {
       return caUserDao.add(raUser);
    }

    @Override
    public boolean modify(CaUser raUser) throws Exception {
        return caUserDao.modify(raUser);
    }

    @Override
    public boolean delete(CaUser raUser) throws Exception {
        return caUserDao.delete(raUser);
    }

    @Override
    public void delete(int id) throws Exception {
        caUserDao.delete(id);
    }


/*    @Override
    public boolean reCastRaUser(CaUser raUser) throws Exception {
        return caUserDao.reCastRaUser(raUser);
    }

    @Override
    public boolean stopRaUser(CaUser raUser) throws Exception {
        return caUserDao.stopRaUser(raUser);
    }

    @Override
    public boolean sleepRaUser(CaUser raUser) throws Exception {
        return caUserDao.sleepRaUser(raUser);
    }*/

    @Override
    public CaUser findById(int id) throws Exception {
        return caUserDao.findById(id);
    }

    @Override
    public PageResult findByPages(String username,/*String userid,String phone,String email,String status ,String on,*/int start, int limit) throws Exception {
        return caUserDao.findByPages(username,/* userid, phone, email, status,on,*/start,limit);
    }

    @Override
    public CaUser findByCn(String cn) throws Exception {
        return caUserDao.findByCn(cn);
    }
/*
    @Override
    public boolean updateCaStatus(CaUser caUser) throws Exception {
        return caUserDao.updateCaStatus(caUser);
    }*/

    @Override
    public boolean update(CaUser caUser) throws Exception {
        return caUserDao.update(caUser);
    }


}
