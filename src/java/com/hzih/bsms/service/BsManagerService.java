package com.hzih.bsms.service;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.BsManager;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-22
 * Time: 下午3:15
 * To change this template use File | Settings | File Templates.
 */
public interface BsManagerService {
    public boolean add(BsManager bsManager)throws Exception;

    public boolean modify(BsManager bsManager)throws Exception;

    public boolean delete(BsManager bsManager)throws Exception;

    public BsManager findById(int id)throws Exception;

    public PageResult findByPages(int start, int limit)throws Exception;
}
