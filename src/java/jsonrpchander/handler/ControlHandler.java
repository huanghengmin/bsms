package jsonrpchander.handler;

import json.JSONUtils;
import jsonrpc2.JSONRPC2Error;
import jsonrpc2.JSONRPC2Request;
import jsonrpc2.JSONRPC2Response;
import jsonrpc2.server.MessageContext;
import jsonrpc2.server.RequestHandler;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-4
 * Time: 下午4:56
 * To change this template use File | Settings | File Templates.
 */
public class ControlHandler implements RequestHandler {
    private Logger logger = Logger.getLogger(ControlHandler.class);

    @Override
    public String[] handledRequests() {
        return new String[]{"VPN.infoMessage"};
    }

    @Override
    public JSONRPC2Response process(JSONRPC2Request request, MessageContext requestCtx) {
        if (request.getMethod().equals("VPN.infoMessage")) {
            List params = (List)request.getPositionalParams();


            Object username = params.get(0);

            Object virtual_address = params.get(1);

            Object certId = params.get(2);

            Object action = params.get(3);

            Object time = params.get(4);



            String rs = JSONUtils.toJSONString(JSONObject.fromObject(""));

            rs = rs.replace("inter","interface");

            return new JSONRPC2Response(rs, request.getID());
        }
        else {
            return new JSONRPC2Response(JSONRPC2Error.METHOD_NOT_FOUND, request.getID());
        }
    }
}