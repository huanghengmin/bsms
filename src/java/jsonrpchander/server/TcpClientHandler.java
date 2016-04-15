package jsonrpchander.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TcpClientHandler extends IoHandlerAdapter {

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        System.out.println(message);//显示接收到的消息
//        session.write("服务器接收成功");
    }
    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("服务器已发送:"+message);//显示发送的消息
    }

}