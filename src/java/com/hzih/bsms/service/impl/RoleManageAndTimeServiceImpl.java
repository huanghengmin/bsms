package com.hzih.bsms.service.impl;

import com.hzih.bsms.dao.RoleManageAndTimeDao;
import com.hzih.bsms.domain.RoleManageAndTime;
import com.hzih.bsms.service.RoleManageAndTimeService;

import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-29
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
public class RoleManageAndTimeServiceImpl implements RoleManageAndTimeService{
    RoleManageAndTimeDao roleManageAndTimeDao;

    public RoleManageAndTimeDao getRoleManageAndTimeDao() {
        return roleManageAndTimeDao;
    }
    public void setRoleManageAndTimeDao(RoleManageAndTimeDao roleManageAndTimeDao) {
        this.roleManageAndTimeDao = roleManageAndTimeDao;
    }

    @Override
    public void addRoleManageAndTime(int rolenameid, int timetype, Date starttime, Date endTime) {
        roleManageAndTimeDao.addRoleManageAndTime( new RoleManageAndTime(rolenameid,timetype,starttime,endTime) );
    }

}
