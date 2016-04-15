package com.hzih.bsms.syslog;

import com.hzih.bsms.entity.IpPort;
import com.hzih.bsms.utils.JDomUtil;
import com.inetec.common.exception.Ex;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hhm
 * Date: 12-10-26
 * Time: 下午2:39
 * To change this template use File | Settings | File Templates.
 */
public class Configuration {
    private String xmlFilePath;
    public Configuration(String xml) {
        xmlFilePath =xml;
    }

    public List<IpPort> getSysLogClient() throws Ex {
        JDomUtil jDomUtils = new JDomUtil();
      return jDomUtils.findAll(xmlFilePath);
    }

    public String add(IpPort ipPort){
         JDomUtil jDomUtils = new JDomUtil();
        return jDomUtils.add(xmlFilePath,ipPort);
    }
    public boolean delete(IpPort ipPort){
         JDomUtil jDomUtils = new JDomUtil();
        return jDomUtils.delete(xmlFilePath,ipPort);
    }

    public String update(IpPort ipPort,IpPort newIpPort){
        JDomUtil jDomUtils = new JDomUtil();
        return jDomUtils.update(xmlFilePath,ipPort,newIpPort);
    }
}
