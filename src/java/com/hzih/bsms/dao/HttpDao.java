package com.hzih.bsms.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.Http;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-22
 * Time: 下午2:55
 * To change this template use File | Settings | File Templates.
 */
public interface HttpDao extends BaseDao {
    public boolean add(Http http)throws Exception;

    public boolean modify(Http http)throws Exception;

    public boolean delete(Http http)throws Exception;

    public PageResult findByPages(int start, int limit)throws Exception;

}
