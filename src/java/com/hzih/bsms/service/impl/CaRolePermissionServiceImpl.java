package com.hzih.bsms.service.impl;

import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.dao.CaPermissionDao;
import com.hzih.bsms.dao.CaRolePermissionDao;
import com.hzih.bsms.domain.CaPermission;
import com.hzih.bsms.domain.CaRolePermission;
import com.hzih.bsms.service.CaRolePermissionService;

import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-27
 * Time: 下午10:59
 * To change this template use File | Settings | File Templates.
 */
public class CaRolePermissionServiceImpl implements CaRolePermissionService{
    private CaPermissionDao caPermissionDao;
    private CaRolePermissionDao caRolePermissionDao;

    public CaPermissionDao getCaPermissionDao() {
        return caPermissionDao;
    }

    public void setCaPermissionDao(CaPermissionDao caPermissionDao) {
        this.caPermissionDao = caPermissionDao;
    }

    public CaRolePermissionDao getCaRolePermissionDao() {
        return caRolePermissionDao;
    }

    public void setCaRolePermissionDao(CaRolePermissionDao caRolePermissionDao) {
        this.caRolePermissionDao = caRolePermissionDao;
    }

    @Override
    public PageResult findCaPermissionsByOtherRoleId(int roleId, int start, int limit) {
        return caRolePermissionDao.findCaPermissionsByOtherRoleId(roleId,start,limit);
    }

    @Override
    public String  getPerminssionsByRoleId(int roleId, int start, int limit) throws Exception {
        PageResult ps = caRolePermissionDao.getPerminssionsByRoleId(roleId,start,limit);
        StringBuilder json = new StringBuilder();
        if(ps!=null){
            List<CaRolePermission> list = ps.getResults();
            int count =  ps.getAllResultsAmount();
            if(list!=null){
                json.append("{success:true,total:" + count + ",rows:[");
                Iterator<CaRolePermission> rolePermissionIterator = list.iterator();
                while (rolePermissionIterator.hasNext()){
                    CaRolePermission caRolePermission = rolePermissionIterator.next();
                    if(caRolePermission!=null){
                        CaPermission log = caPermissionDao.findById(caRolePermission.getCa_permission_id());
                        if(rolePermissionIterator.hasNext()){
                            json.append("{");
                            json.append("id:'").append(log.getId());
                            json.append("',url:'" ).append( log.getUrl()).append("'");
                            json.append("},");
                        }else {
                            json.append("{");
                            json.append("id:'").append(log.getId());
                            json.append("',url:'" ).append( log.getUrl()).append("'");
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
    public void addPermissionsToRoleId(String pIds, int roleId) throws Exception {
        caRolePermissionDao.addPermissionsToRoleId(pIds,roleId);
    }

    @Override
    public void addPermissionToRoleId(int pId, int roleId) throws Exception {
        caRolePermissionDao.addPermissionToRoleId(pId,roleId);
    }

    @Override
    public boolean delAllCaPermissionsByRoleId(int roleId) throws Exception {
        return caRolePermissionDao.delAllCaPermissionsByRoleId(roleId);
    }

    @Override
    public void delByRoleIdAndPermissionId(int i, int i1)throws Exception {
        caRolePermissionDao.delByRoleIdAndPermissionId(i,i1);
    }

    @Override
    public void delByPermissionId(int i) throws Exception {
        caRolePermissionDao.delByPermissionId(i);
    }
}
