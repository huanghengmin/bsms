package com.hzih.bsms.service;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.CaRole;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午2:15
 * To change this template use File | Settings | File Templates.
 */
public interface CaRoleService {
    public boolean add(CaRole caRole)throws Exception;

    public boolean modify(CaRole caRole)throws Exception;

    public boolean delete(CaRole caRole)throws Exception;

    public CaRole findById(int id)throws Exception;

    public PageResult findByPages(String role_name, int start, int limit)throws Exception;

    void allow(CaRole ca_role);

    void stop(CaRole ca_role);
}
