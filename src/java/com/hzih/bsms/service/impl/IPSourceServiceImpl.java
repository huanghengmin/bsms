package com.hzih.bsms.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.IPSourceDao;
import com.hzih.bsms.domain.IPSource;
import com.hzih.bsms.service.IPSourceService;

/**
 * Created with IntelliJ IDEA.
 * User: hhm
 * Date: 13-11-21
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */
public class IPSourceServiceImpl implements IPSourceService {
    private IPSourceDao ipSourceDao;

    public IPSourceDao getIpSourceDao() {
        return ipSourceDao;
    }

    public void setIpSourceDao(IPSourceDao ipSourceDao) {
        this.ipSourceDao = ipSourceDao;
    }

    @Override
    public boolean add(IPSource ipSource) throws Exception {
     return ipSourceDao.add(ipSource);
    }

    @Override
    public boolean modify(IPSource ipSource) throws Exception {
        return ipSourceDao.modify(ipSource);
    }

    @Override
    public boolean delete(IPSource ipSource) throws Exception {
       return ipSourceDao.delete(ipSource);
    }

    @Override
    public IPSource findById(int id) throws Exception {
        return ipSourceDao.findById(id);
    }

    @Override
    public PageResult findByPages(int start, int limit) throws Exception {
       return ipSourceDao.findByPages(start,limit);
    }

    @Override
    public IPSource checkNet(String url, String netMask) {
       return ipSourceDao.checkNet(url,netMask);
    }
}
