package com.hzih.ssl.core.minatls.tls;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class TempTLSClient {

    private static final int PORT = 50003;

    private static final String TARGET_IP = "192.168.1.8";

    public static void main(String[] args) throws GeneralSecurityException {
        /** 创建客户端连接器 **/
        IoConnector connector = new NioSocketConnector();
        /** 设置事件处理器 **/
        connector.setHandler(new TempTLSClientHandler());
        /** 设置编码过滤器和按行读取数据模式 **/
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        /** 创建连接 **/
        ConnectFuture future = connector.connect(new InetSocketAddress(TARGET_IP, PORT));
        /** 等待连接创建完成 **/
        future.awaitUninterruptibly();
        /** 获取连接会话 **/
        IoSession session = future.getSession();
        /** 发送信息 **/
        session.write("HELLO");
        /** 等待连接断开 **/
        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }
}
