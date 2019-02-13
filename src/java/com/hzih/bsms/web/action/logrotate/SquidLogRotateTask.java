package com.hzih.bsms.web.action.logrotate;

import com.hzih.bsms.dao.CaUserDao;
import com.hzih.bsms.domain.CaUser;
import com.hzih.bsms.syslog.SysLogSend;
import com.hzih.bsms.utils.StringContext;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.TimerTask;

public class SquidLogRotateTask extends TimerTask {
    private Logger logger = Logger.getLogger(SquidLogRotateTask.class);

    private CaUserDao caUserDao;

    public SquidLogRotateTask(CaUserDao caUserDao) {
        this.caUserDao = caUserDao;
    }

    @Override
    public void run() {
        /**
         * 切割日志并处理
         */
        SquidLogRotate.rotateSquidLog();
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<File> fileList = SquidFileRegex.getFiles(StringContext.squidPath + "/var/logs", "access.log");
        if (fileList != null && fileList.size() > 0) {
            for (File f : fileList) {
                SquidLogRotate squidLogRotate = new SquidLogRotate();
                List<SquidLogObj> squidLogObjs = squidLogRotate.readLogObjs(f);
                if (squidLogObjs != null && squidLogObjs.size() > 0) {
                    for (SquidLogObj obj : squidLogObjs) {
                        String host = obj.getHost();
                        CaUser user = caUserDao.findByHost(host);
                        if (user != null) {
                            String username = user.getCn();
                            String msg = "用户:" + username + ",请求信息:" + obj.getRequest_msg() + ",请求浏览器信息:" + obj.getClient_msg() + ",返回码:" + obj.getResult_code() + ",请求流量：" + obj.getRequest_bytes() + ",返回流量：" + obj.getReply_bytes() + ",主机地址:" + obj.getHost() + ",时间:" + obj.getDate();
                            logger.info(msg);
                            //SysLogSend.sysLog(msg);
                        }
                    }
                    f.delete();
                }
            }
        }
    }
}