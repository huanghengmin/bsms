package jsonrpchander.server;

import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;


public class TcpServer {

    private int port = 8292;
    private int idleTime = 1800;
    private int bufferSize = 1024;
    private SocketAcceptor acceptor;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(int idleTime) {
        this.idleTime = idleTime;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public SocketAcceptor getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(SocketAcceptor acceptor) {
        this.acceptor = acceptor;
    }

    public void start() {
        acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);


      /*  TextLineCodecFactory lineCodec = new TextLineCodecFactory(Charset.forName("UTF-8"), LineDelimiter.WINDOWS.getValue(),LineDelimiter.WINDOWS.getValue());
        lineCodec.setDecoderMaxLineLength(2*1024*1024);
        lineCodec.setEncoderMaxLineLength(2*1024*1024);
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(lineCodec));*/
        acceptor.getFilterChain().addLast( "logger", new LoggingFilter() );
        acceptor.getFilterChain().addLast("exceutor", new ExecutorFilter(Executors.newCachedThreadPool()));
        acceptor.setHandler(new TcpServerHandler());
//        acceptor.setReuseAddress(true);
//        acceptor.setBacklog(10240);
//        acceptor.getSessionConfig().setReuseAddress(true);
//        acceptor.getSessionConfig().setReadBufferSize(bufferSize);
//        acceptor.getSessionConfig().setReceiveBufferSize(bufferSize);
//        acceptor.getSessionConfig().setTcpNoDelay(true);
//        acceptor.getSessionConfig().setSoLinger(-1);
        // 单位秒
//        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, idleTime);

        try {
            acceptor.bind(new InetSocketAddress(port));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void stop() {
        acceptor.dispose();
    }

  /*  public static void main(String args[])throws Exception{
        TcpServer tcpServer = new TcpServer();
        tcpServer.start();
    }*/

}