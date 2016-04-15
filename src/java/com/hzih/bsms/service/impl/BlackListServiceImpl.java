package com.hzih.bsms.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.BlackListDao;
import com.hzih.bsms.domain.BlackList;
import com.hzih.bsms.service.BlackListService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 下午5:15
 * To change this template use File | Settings | File Templates.
 */
public class BlackListServiceImpl implements BlackListService{

    private BlackListDao blackListDao;

    public BlackListDao getBlackListDao() {
        return blackListDao;
    }

    public void setBlackListDao(BlackListDao blackListDao) {
        this.blackListDao = blackListDao;
    }

    @Override
    public List<BlackList> findAll(){
        return blackListDao.findAll();
    }

    @Override
    public BlackList checkUrl(String url) throws Exception {
        return blackListDao.checkUrl(url);
    }

    @Override
    public boolean add(BlackList blackList) throws Exception {
        return blackListDao.add(blackList);
    }

    @Override
    public boolean modify(BlackList blackList) throws Exception {
        return blackListDao.modify(blackList);
    }

    @Override
    public boolean delete(BlackList blackList) throws Exception {
        return blackListDao.delete(blackList);
    }

    @Override
    public PageResult findByPages(String url, int start, int limit) throws Exception {
        return blackListDao.findByPages(url,start,limit);
    }


    
}
