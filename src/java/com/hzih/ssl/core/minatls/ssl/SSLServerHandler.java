package com.hzih.ssl.core.minatls.ssl;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.ssl.SslFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * @author giftsam
 */
public class SSLServerHandler extends IoHandlerAdapter
{
    private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());
    private int idleTimeout = 10;

    @Override
    public void sessionOpened(IoSession session)
    {
// set idle time to 10 seconds
        session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, idleTimeout);

        session.setAttribute("Values: ");
    }

    @Override
    public void messageReceived(IoSession session, Object message)
    {
        System.out.println("Message received in the server..");
        System.out.println("Message is: " + message.toString());

        SSLSession sslSession = (SSLSession) session.getAttribute(SslFilter.SSL_SESSION);
        logger.info(sslSession.toString());
         String resp = "数字证书用户\n";
        Certificate[] a = new Certificate[0];
        try {
            a = sslSession.getPeerCertificates();
        } catch (SSLPeerUnverifiedException e) {
            logger.warn("读取SSL证书出错!", e);
        }

        String subjectDN = null;

        if (a.length >= 1) {
            X509Certificate x509 = (X509Certificate) a[0];
            subjectDN = x509.getSubjectDN().getName();
        }
        
        resp = resp + "证书验证通过,用户DN="+subjectDN+",允许正常访问资源!";
        session.write(resp);
        logger.info(resp);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
    {
        logger.info("Transaction is idle for " + idleTimeout + "secs, So disconnecting..");
// disconnect an idle client
        session.close(true);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
    {
// close the connection on exceptional situation
        session.close(true);
    }
}

