package com.hzih.syslog.code;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class SyslogCodecFactory implements ProtocolCodecFactory {
    ProtocolDecoder decoder;


    public SyslogCodecFactory(String charset) {
        //To change body of created methods use File | Settings | File Templates.
         decoder = new SyslogProtocolDecoder();
        ((SyslogProtocolDecoder)decoder).setCharset(charset);
    }


    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return null;
    }

    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }
}