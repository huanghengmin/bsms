package com.hzih.bsms.syslog.code;

import com.hzih.bsms.syslog.SyslogMessage;
import com.inetec.common.config.nodes.IpMac;
import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.Charset;

public class SyslogProtocolDecoder implements ProtocolDecoder {
    final static Logger logger = Logger.getLogger(SyslogProtocolDecoder.class);
    private static final AttributeKey BUF_BYTE = new AttributeKey(SyslogProtocolDecoder.class, "syslog");
    private String charset;

    public SyslogProtocolDecoder(String charset) {
        this.charset = charset;
    }

    public void decode(IoSession ioSession, IoBuffer ioBuffer,ProtocolDecoderOutput protocolDecoderOutput)throws Exception {
        try {
            IoBuffer bufTmp = ioBuffer;
            while (bufTmp.remaining() >= 4) {

//                File infile = new File(inFilename);
//                RandomAccessFile raf = new RandomAccessFile(infile, "r");
//                MappedByteBuffer mbb = raf.getChannel().map(FileChannel.MapMode.READ_ONLY,0,6000);
//                Charset inCharset = Charset.forName("UTF-8");
//                Charset outCharset = Charset.forName("UTF-8");

//                CharsetDecoder inDecoder = inCharset.newDecoder();
//                CharsetEncoder outEncoder = outCharset.newEncoder();
//                CharBuffer cb = inCharset.decode();
//
//                ByteBuffer outbb = outCharset.encode(cb);

//                CharSequence str = new String(outbb.array());
//                System.out.println("str is :"+str);

                String data = bufTmp.getString(Charset.forName("gbk").newDecoder());
                int n = data.indexOf(">");
                SyslogMessage syslogMessage = new SyslogMessage();
                if (n > 0) {
                    int facility = SyslogMessage.extractFacility(Integer.parseInt(data.substring(1, n)));
                    int priority = SyslogMessage.extractPriority(Integer.parseInt(data.substring(1, n)));
                    int serverty = SyslogMessage.serverty(priority, facility);
                    syslogMessage.setServerty(priority);
                    syslogMessage.setFacility(facility);
                    int msgn = data.indexOf("]:", n) + 2;
                    if (!data.matches("]:"))
                        msgn = n + 1;
                    syslogMessage.setHostName(IpMac.getMinaRemoteIp(ioSession.getRemoteAddress().toString()));
                    syslogMessage.setMessage(data.substring(msgn));
                } else {
                    logger.warn("Syslog format is error:" + data);
                }
                /* ProtobufResponse pak = new ProtobufResponse();
               pak.setResByteLen(dataLen);
               pak.readFrom(b, 4);*/
                protocolDecoderOutput.write(syslogMessage);
            }

            bufTmp.free();
        } catch (Exception ex) {
            logger.warn("Tcp Server decode data Exception:"+ex.getMessage());
        }
    }

    public void dispose(IoSession session) throws Exception {
        System.out.println("dispose");
    }

    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
        System.out.println("finishDecode");
    }

}