package com.hzih.bsms.service;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.CaUser;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-24
 * Time: 下午6:14
 * To change this template use File | Settings | File Templates.
 */
public interface CaUserService {

    public CaUser findBySerialNumber(String serialNumber)throws Exception;

    public CaUser checkUserName(String username)throws Exception;
    
    public boolean add(CaUser raUser)throws Exception;

    public boolean modify(CaUser raUser)throws Exception;

    public boolean delete(CaUser raUser)throws Exception;

    public void delete(int id) throws Exception;

    /*public boolean reCastRaUser(CaUser raUser)throws Exception;

    public boolean stopRaUser(CaUser raUser)throws Exception;

    public boolean sleepRaUser(CaUser raUser)throws Exception;*/

    public CaUser findById(int id)throws Exception;

    public PageResult findByPages(String username,/*String userid,String phone,String email,String status ,String on,*/ int start, int limit)throws Exception;
    
    public CaUser findByCn(String cn)throws Exception;

   /* boolean updateCaStatus(CaUser caUser) throws Exception;*/

    boolean update(CaUser caUser)throws Exception;
}
