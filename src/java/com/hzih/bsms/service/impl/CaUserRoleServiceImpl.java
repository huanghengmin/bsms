package com.hzih.bsms.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.CaUserDao;
import com.hzih.bsms.dao.CaUserRoleDao;
import com.hzih.bsms.domain.CaUser;
import com.hzih.bsms.domain.CaUserRole;
import com.hzih.bsms.service.CaUserRoleService;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午10:58
 * To change this template use File | Settings | File Templates.
 */
public class CaUserRoleServiceImpl implements CaUserRoleService{
    private CaUserRoleDao caUserRoleDao;
    private CaUserDao caUserDao;

    public CaUserRoleDao getCaUserRoleDao() {
        return caUserRoleDao;
    }

    public void setCaUserRoleDao(CaUserRoleDao caUserRoleDao) {
        this.caUserRoleDao = caUserRoleDao;
    }

    public CaUserDao getCaUserDao() {
        return caUserDao;
    }

    public void setCaUserDao(CaUserDao caUserDao) {
        this.caUserDao = caUserDao;
    }

    @Override
    public String getUsersByRoleId(int roleId, int start, int limit) throws Exception {
        PageResult ps = caUserRoleDao.getUsersByRoleId(roleId,start,limit);
        StringBuilder json = new StringBuilder();
        if(ps!=null){
            List<CaUserRole> list = ps.getResults();
            int count =  ps.getAllResultsAmount();
            if(list!=null){
                json.append("{success:true,total:" + count + ",rows:[");
                Iterator<CaUserRole> raUserIterator = list.iterator();
                while (raUserIterator.hasNext()){
                    CaUserRole caUserRole = raUserIterator.next();
                    if(caUserRole!=null){
                        CaUser log = caUserDao.findById(caUserRole.getCa_user_id());
                        if(raUserIterator.hasNext()){
                            json.append("{" );
                            json.append("id:'").append(log.getId());
                            json.append("',cn:'" ).append( log.getCn());
                            json.append("',hzihpassword:'" ).append( log.getHzihpassword() );
                            json.append("',hzihid:'" ).append( log.getHzihid());
                            json.append("',hzihphone:'").append( log.getHzihphone() );
                            json.append("',hzihaddress:'" ).append( log.getHzihaddress() );
                            json.append("',hzihemail:'").append( log.getHzihemail());
                            json.append("',hzihjobnumber:'" ).append( log.getHzihjobnumber());
                            json.append("',phonenetid:'" ).append( log.getPhonenetid());
                            json.append("',hzihcaserialNumber:'").append( log.getHzihcaserialNumber() );
                            json.append("',terminalid:'" ).append( log.getTerminalid());
//                            json.append("',terminal_pwd:'" ).append(log.getTerminal_pwd() );
//                            json.append("',terminal_pwd_audit:'" ).append( log.getTerminal_pwd_audit() );
                            json.append("',hzihdn:'" ).append( log.getHzihdn() );
                            json.append("',hzihprovince:'" ).append( log.getHzihprovince());
                            json.append("',hzihcity:'" ).append( log.getHzihcity());
                            json.append("',hzihorganization:'" ).append( log.getHzihorganization());
                            json.append("',hzihinstitutions:'" ).append( log.getHzihinstitutions() );
                            json.append("',hzihcastatus:'" ).append( log.getHzihcastatus() );
                            json.append("',hzihparentca:'" ).append( log.getHzihparentca() );
                            json.append("',hzihcavalidity:'").append( log.getHzihcavalidity());
                            json.append("',status:'" ).append(log.getStatus());
                            json.append("',hzihcertificatetype:'").append(log.getHzihcertificatetype()).append( "'" );
                            json.append("},");
                        }else {
                            json.append("{" );
                            json.append("id:'").append(log.getId());
                            json.append("',cn:'" ).append( log.getCn());
                            json.append("',hzihpassword:'" ).append( log.getHzihpassword() );
                            json.append("',hzihid:'" ).append( log.getHzihid());
                            json.append("',hzihphone:'").append( log.getHzihphone() );
                            json.append("',hzihaddress:'" ).append( log.getHzihaddress() );
                            json.append("',hzihemail:'").append( log.getHzihemail());
                            json.append("',hzihjobnumber:'" ).append( log.getHzihjobnumber());
                            json.append("',phonenetid:'" ).append( log.getPhonenetid());
                            json.append("',hzihcaserialNumber:'").append( log.getHzihcaserialNumber() );
                            json.append("',terminalid:'" ).append( log.getTerminalid());
//                            json.append("',terminal_pwd:'" ).append(log.getTerminal_pwd() );
//                            json.append("',terminal_pwd_audit:'" ).append( log.getTerminal_pwd_audit() );
                            json.append("',hzihdn:'" ).append( log.getHzihdn() );
                            json.append("',hzihprovince:'" ).append( log.getHzihprovince());
                            json.append("',hzihcity:'" ).append( log.getHzihcity());
                            json.append("',hzihorganization:'" ).append( log.getHzihorganization());
                            json.append("',hzihinstitutions:'" ).append( log.getHzihinstitutions() );
                            json.append("',hzihcastatus:'" ).append( log.getHzihcastatus() );
                            json.append("',hzihparentca:'" ).append( log.getHzihparentca() );
                            json.append("',hzihcavalidity:'").append( log.getHzihcavalidity());
                            json.append("',status:'" ).append(log.getStatus());
                            json.append("',hzihcertificatetype:'").append(log.getHzihcertificatetype()).append( "'" );
                            json.append("}");
                        }
                    }
                }
            }
            json.append("]}");
        }
        return json.toString();
    }

    @Override
    public void addUsersToRoleId(String uIds, int roleId) throws Exception {
        caUserRoleDao.addUsersToRoleId(uIds,roleId);
    }

    @Override
    public void addUserToRoleId(int uId, int roleId) throws Exception {
        caUserRoleDao.addUserToRoleId(uId,roleId);
    }

    @Override
    public PageResult findCaUserByOtherRoleId(int roleId, int start, int limit) {
        return caUserRoleDao.findCaUserByOtherRoleId(roleId,start,limit);
    }

    @Override
    public boolean delAllByRoleId(int roleId) throws Exception {
        return caUserRoleDao.delAllByRoleId(roleId);
    }

    @Override
    public void delByRoleIdAndUserId(int i, int i1)throws Exception {
        caUserRoleDao.delByRoleIdAndUserId(i,i1);
    }

    @Override
    public void delByUserId(int i) throws Exception {
        caUserRoleDao.delByUserId(i);
    }

    @Override
    public List<CaUserRole> findByUserId(int id) throws Exception {
        return caUserRoleDao.findByUserId(id);
    }
}
