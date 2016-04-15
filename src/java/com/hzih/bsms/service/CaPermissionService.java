package com.hzih.bsms.service;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.CaPermission;

import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午2:16
 * To change this template use File | Settings | File Templates.
 */
public interface CaPermissionService {
    public boolean add(CaPermission caPermission)throws Exception;

    public boolean modify(CaPermission caPermission)throws Exception;

    public boolean delete(CaPermission caPermission)throws Exception;

    public CaPermission findById(int id)throws Exception;

    public PageResult findByPages(String url,int start, int limit)throws Exception;

    public CaPermission checkUrl(String url);

    public List<CaPermission> findAll();
}
