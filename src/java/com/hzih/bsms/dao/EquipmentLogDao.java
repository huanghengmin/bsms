package com.hzih.bsms.dao;

import cn.collin.commons.dao.BaseDao;
import cn.collin.commons.domain.PageResult;
import com.hzih.bsms.domain.EquipmentLog;

import java.util.Date;

public interface EquipmentLogDao extends BaseDao {
	/**
	 * 分页查询EquipmentLog
	 * 
	 */
	public PageResult listLogsByParams(int pageIndex, int pageLength,
                                       Date startDate, Date endDate, String logLevel, String equipmentName);

    void truncate() throws Exception;

    public boolean findByDateAndLogInfo(EquipmentLog equipmentLog) throws Exception;

    public void delete(String startDate, String endDate, String logLevel, String equipmentName) throws Exception;
}
