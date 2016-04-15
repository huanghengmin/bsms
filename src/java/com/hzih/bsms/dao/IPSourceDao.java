package com.hzih.bsms.dao;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.IPSource;

/**
 * Created with IntelliJ IDEA.
 * User: hhm
 * Date: 13-11-21
 * Time: 下午2:16
 * To change this template use File | Settings | File Templates.
 */
public interface IPSourceDao {
    public boolean add(IPSource ipSource)throws Exception;

    public boolean modify(IPSource ipSource)throws Exception;

    public boolean delete(IPSource ipSource)throws Exception;

    public IPSource findById(int id)throws Exception;

    public PageResult findByPages(int start, int limit)throws Exception;

    public IPSource checkNet(String ip,String netMask);
}
