package com.hzih.bsms.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.WhiteListDao;
import com.hzih.bsms.domain.WhiteList;
import com.hzih.bsms.service.WhiteListService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-27
 * Time: 下午5:16
 * To change this template use File | Settings | File Templates.
 */
public class WhiteListServiceImpl implements WhiteListService{
    private WhiteListDao whiteListDao;

    public WhiteListDao getWhiteListDao() {
        return whiteListDao;
    }

    public void setWhiteListDao(WhiteListDao whiteListDao) {
        this.whiteListDao = whiteListDao;
    }

    @Override
    public List<WhiteList> findAll(){
      return  whiteListDao.findAll();
    }

    @Override
    public WhiteList checkUrl(String url) throws Exception {
        return whiteListDao.checkUrl(url);
    }

    @Override
    public boolean add(WhiteList whiteList) throws Exception {
        return whiteListDao.add(whiteList);
    }

    @Override
    public boolean modify(WhiteList whiteList) throws Exception {
        return whiteListDao.modify(whiteList);
    }

    @Override
    public boolean delete(WhiteList whiteList) throws Exception {
        return whiteListDao.delete(whiteList);
    }


    @Override
    public PageResult findByPages(String url,int start, int limit) throws Exception {
        return whiteListDao.findByPages(url,start,limit);
    }



}
