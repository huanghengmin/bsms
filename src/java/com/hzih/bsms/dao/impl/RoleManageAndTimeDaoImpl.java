package com.hzih.bsms.dao.impl;

import com.hzih.bsms.dao.RoleManageAndTimeDao;
import com.hzih.bsms.domain.RoleManageAndTime;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Created by IntelliJ IDEA.
 * User: cx
 * Date: 12-11-29
 * Time: 上午11:19
 * To change this template use File | Settings | File Templates.
 */
public class RoleManageAndTimeDaoImpl extends HibernateDaoSupport implements RoleManageAndTimeDao {

    @Override
    public void addRoleManageAndTime(RoleManageAndTime roleManageAndTime) {
        this.getHibernateTemplate().save(roleManageAndTime);
    }
}
