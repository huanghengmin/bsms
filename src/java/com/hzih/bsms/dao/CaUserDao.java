package com.hzih.bsms.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.CaUser;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-24
 * Time: 下午5:01
 * To change this template use File | Settings | File Templates.
 */
public interface CaUserDao extends BaseDao {
    public CaUser findBySerialNumber(String serialNumber)throws Exception;

    public boolean add(CaUser caUser)throws Exception;

    public boolean modify(CaUser caUser)throws Exception;

    public boolean delete(CaUser caUser)throws Exception;

    public boolean reCastRaUser(CaUser caUser)throws Exception;

    public boolean stopRaUser(CaUser caUser)throws Exception;

    public boolean sleepRaUser(CaUser caUser)throws Exception;

    public CaUser findById(int id)throws Exception;

    public PageResult findByPages(String username,String userid,String phone,String email,String status ,String on,int start, int limit)throws Exception;

    public CaUser checkUserName(String username)throws Exception;

    public CaUser findByCn(String cn)throws Exception;

    boolean updateCaStatus(CaUser caUser) throws Exception;

    boolean update(CaUser caUser)throws Exception;

    public CaUser findByHost(String host);
}
