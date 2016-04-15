package com.hzih.bsms.service;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.IPSource;

/**
 * Created with IntelliJ IDEA.
 * User: hhm
 * Date: 13-11-21
 * Time: 下午2:27
 * To change this template use File | Settings | File Templates.
 */
public interface IPSourceService  {
    public boolean add(IPSource ipSource)throws Exception;

    public boolean modify(IPSource ipSource)throws Exception;

    public boolean delete(IPSource ipSource)throws Exception;

    public IPSource findById(int id)throws Exception;

    public PageResult findByPages(int start, int limit)throws Exception;

    public IPSource checkNet(String url, String netMask);
}
