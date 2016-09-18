package com.hzih.bsms.web.action.bs;

import com.hzih.bsms.domain.BlackList;
import com.hzih.bsms.domain.WhiteList;
import com.hzih.bsms.service.BlackListService;
import com.hzih.bsms.service.LogService;
import com.hzih.bsms.service.WhiteListService;
import com.hzih.bsms.utils.StringContext;
import com.hzih.bsms.web.action.ActionBase;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-18
 * Time: 上午9:21
 * To change this template use File | Settings | File Templates.
 */
public class StrategyAction extends ActionSupport {
    private static final String xml = StringContext.systemPath + "/config/strategy.xml";
    //    private static final String xml = "E:/fartec/ichange/ra/config/android_config.xml";
    private Logger logger = Logger.getLogger(StrategyAction.class);
    private LogService logService;
    private BlackListService blackListService;
    private WhiteListService whiteListService;

    public LogService getLogService() {
        return logService;
    }

    public void setLogService(LogService logService) {
        this.logService = logService;
    }

    public BlackListService getBlackListService() {
        return blackListService;
    }

    public void setBlackListService(BlackListService blackListService) {
        this.blackListService = blackListService;
    }

    public WhiteListService getWhiteListService() {
        return whiteListService;
    }

    public void setWhiteListService(WhiteListService whiteListService) {
        this.whiteListService = whiteListService;
    }

    public String findConfig() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String result = actionBase.actionBegin(request);
        int totalCount = 0;
        StringBuilder stringBuilder = new StringBuilder();
        findData(stringBuilder);
        totalCount = totalCount + 1;
        StringBuilder json = new StringBuilder("{totalCount:" + totalCount + ",root:[");
        json.append(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1));
        json.append("]}");
        try {
            actionBase.actionEnd(response, json.toString(), result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAttribute(String elementName) {
        Document document = buildFromFile(xml);
        Element root = document.getRootElement();
        Element child = root.getChild(elementName);
        return child.getText();
    }


    private void findData(StringBuilder stringBuilder) {
        stringBuilder.append("{");
//        if (getAttribute("threeYards") != null)
//            stringBuilder.append("threeYards:'" + getAttribute("threeYards") + "'").append(",");
//        else
//            stringBuilder.append("threeYards:''").append(",");

//        stringBuilder.append("white:'" + getAttribute("stop_url") + "',");
//        stringBuilder.append("black:'" + getAttribute("black") + "'");
//        stringBuilder.append("allow_url:'"+getAttribute("allow_url")+"'");
        stringBuilder.append("stop_url:'"+getAttribute("stop_url")+"'");
        stringBuilder.append("},");
    }

    public String strategy() throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        ActionBase actionBase = new ActionBase();
        String stop_url = request.getParameter("stop_url");
        /*if (white == null)
            white = "0";
        String black = request.getParameter("black");
        if (black == null) {
            black = "0";
        }*/
//        String check_threeYards = request.getParameter("threeYards");
//        String allow_url = request.getParameter("allow_url");
//        if(stop_url==null||!stop_url.equals("on")){
//            stop_url = "off";
//        }
//        if(allow_url==null||!allow_url.equals("on")){
//            allow_url = "off";
//        }
        String result = actionBase.actionBegin(request);
        String json = "{success:false}";
        Document document = buildFromFile(xml);
        if (document != null) {
            Element element = document.getRootElement();
            Element stop_url_el = element.getChild("stop_url");
            if (stop_url_el != null)
                stop_url_el.setText(stop_url);
            else {
                Element _stop_url = new Element("stop_url");
                _stop_url.setText(stop_url);
                element.addContent(_stop_url);
            }

          /*  Element black_el = element.getChild("stop_url");
            if (black_el != null)
                black_el.setText(black);
            else {
                Element _black_url = new Element("black");
                _black_url.setText(black);
                element.addContent(_black_url);
            }*/
          /*  if (check_threeYards != null && check_threeYards.length() > 0) {
                Element three_el = element.getChild("threeYards");
                if (three_el != null)
                    three_el.setText(check_threeYards);
                else {
                    Element _three_el = new Element("threeYards");
                    _three_el.setText(check_threeYards);
                    element.addContent(_three_el);
                }
            }*/
//            element.getChild("allow_url").setText(allow_url);
            XMLOutputter outputter = new XMLOutputter();
            Format fm = Format.getRawFormat();
            fm.setExpandEmptyElements(true);
            fm.setEncoding("utf-8");
            outputter.setFormat(fm);
            FileWriter writer = new FileWriter(xml);
            outputter.output(document, writer);
            writer.close();
            json = "{success:true}";
        } else {
            Element root = new Element("root");
            Element _stop_url = new Element("stop_url");
            _stop_url.setText(stop_url);
            root.addContent(_stop_url);

           /* Element _black_url = new Element("black");
            _black_url.setText(black);
            root.addContent(_black_url);*/

         /*   if (check_threeYards != null) {
                Element _three_el = new Element("threeYards");
                _three_el.setText(check_threeYards);
                root.addContent(_three_el);
            }*/
//            Element _allow_url = new Element("allow_url");
//            _allow_url.setText(allow_url);
//            root.addContent(_allow_url);
            Document doc = new Document(root);
            XMLOutputter outputter = new XMLOutputter();
            Format fm = Format.getRawFormat();
            fm.setExpandEmptyElements(true);
            fm.setEncoding("utf-8");
            outputter.setFormat(fm);
            FileWriter writer = new FileWriter(xml);
            outputter.output(doc, writer);
            writer.close();
            json = "{success:true}";
        }
        BlackWhiteUtils blackWhiteUtils = new BlackWhiteUtils();
        List<BlackList> blackLists = blackListService.findAll();
        List<WhiteList> whiteLists = whiteListService.findAll();
        /*boolean build =*/ blackWhiteUtils.strategy(blackLists,whiteLists);
      /*  if(build){

        }else {

        }*/
        actionBase.actionEnd(response, json, result);
        return null;
    }

    public static Document buildFromFile(String filePath) {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document anotherDocument = builder.build(new File(filePath));
            return anotherDocument;
        } catch (JDOMException e) {
//            logger.error(e.getMessage());
        } catch (NullPointerException e) {
//            logger.error(e.getMessage());
        } catch (IOException e) {
//            logger.error(e.getMessage());
        }
        return null;
    }
}
