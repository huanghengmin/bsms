package com.hzih.bsms.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.CaPermission;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午2:10
 * To change this template use File | Settings | File Templates.
 */
public interface CaPermissionDao extends BaseDao{

    public boolean add(CaPermission caPermission)throws Exception;

    public boolean modify(CaPermission caPermission)throws Exception;

    public boolean delete(CaPermission caPermission)throws Exception;

    public CaPermission findById(int id)throws Exception;

    public PageResult findByPages(String url,int start, int limit)throws Exception;

    public CaPermission checkUrl(String url);
}
