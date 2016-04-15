package com.hzih.bsms.web.action.ra;

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

public class RaConfigXml {
    private static Logger logger=Logger.getLogger(RaConfigXml.class);
    public  static  final String ra_config = "ra_config";
    public  static final  String ra_ip = "ra_ip";
    public  static  final String ra_port = "ra_port";
    public  static  final String jdbc_driverClass = "jdbc_driverClass";
    public  static final  String jdbc_url = "jdbc_url";
    public  static  final String jdbc_user = "jdbc_user";
    public  static  final String jdbc_password = "jdbc_password";
    //    public  static String ra_port = "ra_port";
/*    public  static String bs_ip = "bs_ip";
    public  static String bs_port = "bs_port";*/
    private static  final String path = StringContext.systemPath+"/config/ra_config.xml";

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

    public static void saveConfig(String ra_ip/*,String ra_port*/
                                  /*,String bs_ip,
                                 String bs_port*/){
        Document doc=DocumentHelper.createDocument();
        Element root=doc.addElement(RaConfigXml.ra_config);
        root.addAttribute(RaConfigXml.ra_ip,ra_ip);
        root.addAttribute(RaConfigXml.ra_port,"80");
        root.addAttribute(RaConfigXml.jdbc_driverClass,"com.mysql.jdbc.Driver");
        root.addAttribute(RaConfigXml.jdbc_url,"jdbc:mysql://"+ra_ip+":3306/ra?useUnicode=true&characterEncoding=utf-8");
        root.addAttribute(RaConfigXml.jdbc_user,"root");
        root.addAttribute(RaConfigXml.jdbc_password,"123456");
//        String bs_ip = request.getParameter("bs_ip");
//        String bs_port = request.getParameter("bs_port");
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
