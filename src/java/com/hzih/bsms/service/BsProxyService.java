package com.hzih.bsms.service;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.BsProxy;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-22
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public interface BsProxyService {
    public boolean add(BsProxy bsProxy)throws Exception;

    public boolean modify(BsProxy bsProxy)throws Exception;

    public boolean delete(BsProxy bsProxy)throws Exception;

    public BsProxy findById(int id)throws Exception;

    public PageResult findByPages(int start, int limit)throws Exception;
}
