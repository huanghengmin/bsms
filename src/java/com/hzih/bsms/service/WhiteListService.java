package com.hzih.bsms.service;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.WhiteList;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 下午5:15
 * To change this template use File | Settings | File Templates.
 */
public interface WhiteListService {

    public List<WhiteList> findAll();

    public WhiteList checkUrl(String url)throws Exception;

    public boolean add(WhiteList whiteList)throws Exception;

    public boolean modify(WhiteList whiteList)throws Exception;

    public boolean delete(WhiteList whiteList)throws Exception;

    public PageResult findByPages(String url,int start , int limit)throws Exception;
}
