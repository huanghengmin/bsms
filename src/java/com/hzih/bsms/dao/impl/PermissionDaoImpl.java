package com.hzih.bsms.dao.impl;

import cn.collin.commons.dao.MyDaoSupport;
import com.hzih.bsms.dao.PermissionDao;
import com.hzih.bsms.domain.Permission;

public class PermissionDaoImpl extends MyDaoSupport implements PermissionDao {

	@Override
	public void setEntityClass() {
		this.entityClass = Permission.class;
	}

}
