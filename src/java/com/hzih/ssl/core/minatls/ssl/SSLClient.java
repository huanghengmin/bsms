package com.hzih.ssl.core.minatls.ssl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import javax.net.ssl.SSLContext;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * @author giftsam
 */
public class SSLClient
{
    private static final int REMORT_PORT = 5000;
    private static final String REMORT_HOST = "192.168.1.8";

    public static void main(String[] args) throws IOException, InterruptedException, GeneralSecurityException
    {
        IoConnector connector = new NioSocketConnector();
        connector.getSessionConfig().setReadBufferSize(2048);

        SSLContext sslContext = new SSLContextGenerator().getClientSslContext();

        System.out.println("SSLContext protocol is: " + sslContext.getProtocol());

        SslFilter sslFilter = new SslFilter(sslContext);
        sslFilter.setUseClientMode(true);
        connector.getFilterChain().addFirst("sslFilter", sslFilter);

        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

        connector.setHandler(new SSLClientHandler("Hello Server.."));
        ConnectFuture future = connector.connect(new InetSocketAddress(REMORT_HOST, REMORT_PORT));
        future.awaitUninterruptibly();

        if (!future.isConnected())
        {
            return;
        }
        IoSession session = future.getSession();
        session.getConfig().setUseReadOperation(true);
        session.getCloseFuture().awaitUninterruptibly();
        System.out.println("After Writing");
        connector.dispose();
    }
}
