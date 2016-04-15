package jsonrpchander.server;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

public class TcpClient {
    private String serverIp;
    private int serverPort;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    private SocketConnector connector;
    private ConnectFuture future;
    private IoSession session;

    public SocketConnector getConnector() {
        return connector;
    }

    public void setConnector(SocketConnector connector) {
        this.connector = connector;
    }

    public ConnectFuture getFuture() {
        return future;
    }

    public void setFuture(ConnectFuture future) {
        this.future = future;
    }

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public boolean connect() {
        try {
            // 创建一个socket连接
            connector = new NioSocketConnector();
            // 设置链接超时时间
            connector.setConnectTimeoutMillis(3000);
            // 获取过滤器链
            DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();
            // 添加编码过滤器 处理乱码、编码问题
            TextLineCodecFactory lineCodec = new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(), LineDelimiter.WINDOWS.getValue());
//            lineCodec.setDecoderMaxLineLength(2*1024*1024);
//            lineCodec.setEncoderMaxLineLength(2*1024*1024);
//            filterChain.addLast("codec", new ProtocolCodecFilter(lineCodec));
            filterChain.addLast("exceutor", new ExecutorFilter(Executors.newCachedThreadPool()));

            // 消息核心处理器
            connector.setHandler(new TcpClientHandler());

            // 连接服务器，知道端口、地址
            future = connector.connect(new InetSocketAddress(serverIp, serverPort));
            // 等待连接创建完成
            future.awaitUninterruptibly();
            // 获取当前session
            session = future.getSession();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean close() {
        try {
            CloseFuture future = session.getCloseFuture();
            future.awaitUninterruptibly(1000);
            connector.dispose();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void main(String args[])throws Exception{
        TcpClient tcpClient = new TcpClient();
//        tcpClient.setServerIp("192.168.1.212");
//        tcpClient.setServerIp("127.0.0.1");
        tcpClient.setServerIp("192.168.1.8");
        tcpClient.setServerPort(4300);
        tcpClient.connect();


      /*  tcpClient.getSession().write(
                "{\"id\":1," +
                        "\"method\":\"vpn.getUsers\"," +
                        "\"params\":[\"1\",\"15\"]," +
                        "\"jsonrpc\":\"2.0\"}");*/

        /*vpngetinfo*/
      /*  tcpClient.getSession().write(
                "{\"id\":1," +
                "\"method\":\"vpn.getInfo\"," +
                "\"params\":[\"type\",\"algmode\",\"tunelmode\",\"routes\",\"nics\",\"baseinfo\",\"securitydeviceinfo\"]," +
                "\"jsonrpc\":\"2.0\"}");*/

         /*vpngetmonitorinfo*/
        tcpClient.getSession().write("{\"id\":2,\"method\":\"vpn.getmonitor\",\"jsonrpc\":\"2.0\"}");

         /*kickUser*/
    /*    tcpClient.getSession().write(
                "{\"id\":3," +
                        "\"method\":\"vpn.kickUser\"," +
                        "\"params\":[\"A6B817176303420BA671F646D85999F0\"]," +
                        "\"jsonrpc\":\"2.0\"}");*/

     /*   tcpClient.getSession().write(
                "{\"id\":3," +
                        "\"method\":\"vpn.kickUser\"," +
                        "\"params\":[\"EE4ECE803AE14C4091E5565D52D035F7\"]," +
                        "\"jsonrpc\":\"2.0\"}");*/

   /*     tcpClient.getSession().write(
                "{\"id\":3," +
                        "\"method\":\"vpn.kickUser\"," +
                        "\"params\":[\"E182204CF8F54C51A657B60E9D44051B\"]," +
                        "\"jsonrpc\":\"2.0\"}");*/
        tcpClient.close();
    }

}