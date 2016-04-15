package com.hzih.bsms.dao;

import cn.collin.commons.dao.BaseDao;
import com.hzih.bsms.domain.Role;

public interface RoleDao extends BaseDao {

    public Role findByName(String name) throws Exception;
}
