package jsonrpchander.server;

import jsonrpc2.JSONRPC2ParseException;
import jsonrpc2.JSONRPC2Request;
import jsonrpc2.server.Dispatcher;
import jsonrpchander.handler.*;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

//import java.util.Date;

public class TcpServerHandler extends IoHandlerAdapter{


    @Override
    public void messageReceived(IoSession session, Object message)throws Exception {
//        String str = message.toString();
//        System.out.println("穿过来的消息："+str);
//        if( str.trim().equalsIgnoreCase("quit") ) {
//            session.close();//结束会话
//            return;
//        }
        Dispatcher dispatcher =  new Dispatcher();
        dispatcher.register(new ControlHandler());
//        dispatcher.register(new VpnGetMonitorInfoHandler());
//        dispatcher.register(new VpnGetInfoHandler());
//        dispatcher.register(new VpnKickUserHandler());
//        dispatcher.register(new VpnGetUsersHandler());

        String msg =   ioBufferToString(message);
        JSONRPC2Request request = null;
        try {
            request = JSONRPC2Request.parse(msg);
        } catch (JSONRPC2ParseException e) {
//            System.out.println(e);
            return;
        }
//        JSONRPC2Response resp = dispatcher.process(request, null);
//        Date date = new Date();
//        session.write( "服务器返回的时间："+date.toString() );//返回当前时间的字符串
//        IoBuffer resp_result = stringToIoBuffer(resp.toString());
//        session.write(resp_result);
    }

    @Override
    public void sessionCreated(IoSession session) {
        //显示客户端的ip和端口
//        System.out.println(session.getRemoteAddress().toString());
    }


    /**
     * 将bytebuffer转换成string
     * @param str
     */
    public static IoBuffer stringToIoBuffer(String str)
    {

        byte bt[] = str.getBytes();

        IoBuffer ioBuffer = IoBuffer.allocate(bt.length);
        ioBuffer.put(bt, 0, bt.length);
        ioBuffer.flip();
        return ioBuffer;
    }

    /**
     * 将IoBuffer转换成string
     * @param
     */
    public static String ioBufferToString(Object message)
    {
        if (!(message instanceof IoBuffer))
        {
            return "";
        }
        IoBuffer ioBuffer = (IoBuffer) message;
        byte[] b = new byte [ioBuffer.limit()];
        ioBuffer.get(b);
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < b.length; i++)
        {

            stringBuffer.append((char) b [i]);
        }
        return stringBuffer.toString();
    }
    
    
    public static void main(String args[])throws Exception{
            TcpServer tcpServer = new TcpServer();
            tcpServer.setPort(1155);
            tcpServer.start();
    }

}