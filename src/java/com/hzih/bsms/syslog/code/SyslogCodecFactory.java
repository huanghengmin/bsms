package com.hzih.bsms.syslog.code;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class SyslogCodecFactory implements ProtocolCodecFactory {
    private String charset;

    public SyslogCodecFactory(String charset) {
        this.charset = charset;
    }

    ProtocolDecoder decoder = new SyslogProtocolDecoder(charset);


    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return null;
    }

    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return decoder;
    }
}