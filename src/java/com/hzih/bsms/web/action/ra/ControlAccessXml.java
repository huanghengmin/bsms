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

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-24
 * Time: 下午4:56
 * To change this template use File | Settings | File Templates.
 */
public class ControlAccessXml {
    private static Logger logger=Logger.getLogger(RaConfigXml.class);
    public  static final String control = "control";
    public  static final  String status = "status";
    public  static final  String control_url = "control_url";
    private static final  String path = StringContext.systemPath+"/config/control_config.xml";

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

    public static void saveConfig(String status,String control_url){
        Document doc= DocumentHelper.createDocument();
        Element root=doc.addElement(ControlAccessXml.control);
        root.addAttribute(ControlAccessXml.status,status);
        root.addAttribute(ControlAccessXml.control_url,control_url);
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
