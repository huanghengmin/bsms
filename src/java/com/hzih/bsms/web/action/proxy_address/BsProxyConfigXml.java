package com.hzih.bsms.web.action.proxy_address;

import com.hzih.bsms.utils.StringContext;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;

public class BsProxyConfigXml {
    private static Logger logger=Logger.getLogger(BsProxyConfigXml.class);
    public  static final String bs_proxy_config = "bs_proxy_config";
    public  static final String bs_proxy_ip = "bs_proxy_ip";
//    public  static String vpn_port = "vpn_port";
/*    public  static String bs_ip = "bs_ip";
    public  static String bs_port = "bs_port";*/
    private static final String path = StringContext.systemPath+"/config/bs_proxy_config.xml";

    public static String getAttribute(String attributeName){
        SAXReader saxReader = new SAXReader();
        Document doc=null;
        try {
            doc =saxReader.read(new File(path));
        } catch (DocumentException e) {
            logger.error(e.getMessage());
        }
        Element root = doc.getRootElement();
        String result = root.attributeValue(attributeName);
        return result;
    }

    public static void saveConfig(String vpn_ip/*,String vpn_port*/
    /*,String bs_ip,
    String bs_port*/){
        Document doc=DocumentHelper.createDocument();
        Element root=doc.addElement(BsProxyConfigXml.bs_proxy_config);
        root.addAttribute(BsProxyConfigXml.bs_proxy_ip,vpn_ip);
//        root.addAttribute(BsProxyConfigXml.vpn_port,"80");
    /*    root.addAttribute(BsProxyConfigXml.bs_ip,bs_ip);
        root.addAttribute(BsProxyConfigXml.bs_port,bs_port);*/
        OutputFormat outputFormat=new OutputFormat("",true);
        try {
            XMLWriter xmlWriter=new XMLWriter(new FileOutputStream(new File(path)),outputFormat);
            try {
                xmlWriter.write(doc);
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        } catch (UnsupportedEncodingException e) {
            logger.info(e.getMessage());
        } catch (FileNotFoundException e) {
            logger.info(e.getMessage());
        }
    }
}
