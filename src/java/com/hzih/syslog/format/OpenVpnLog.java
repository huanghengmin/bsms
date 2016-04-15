package com.hzih.syslog.format;




import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class OpenVpnLog implements ILogFormat {

    private static final String S_Logflag = "logflag=\"TBSGS\"";
    private static final String S_ServiceID = "serviceid=\"3\"";
    private static final String S_Separator_Keys = " ";
    private static final String S_Separator_KeyValue = "=";
    private String log;
    private String on;
    private String serial;
    private String logflag;
    private String userip;
    private String vpnip;
    private String accessurl;
    private String orgcode;
    private String username;
    private String identity;
    private String accessreturn;
    private String reason;
    private String tbsgip;
    private String proxycn;


    private String terminalid;
    private String time;
    private String bytes;
    private String upbytes;
    private String serviceid;
    private String level;
    /**
     * id
     */

    public String getVpnip() {
        return vpnip;
    }

    public void setVpnip(String vpnip) {
        this.vpnip = vpnip;
    }
    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEquid() {
        return equid;
    }

    public void setEquid(String equid) {
        this.equid = equid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String equid;
    /*
     *ip
     */
    public String ip;

    public String getLogflag() {
        return logflag;
    }

    public void setLogflag(String logflag) {
        this.logflag = logflag;
    }

    public String getUserip() {
        return userip;
    }

    public void setUserip(String userip) {
        this.userip = userip;
    }

    public String getAccessurl() {
        return accessurl;
    }

    public void setAccessurl(String accessurl) {
        this.accessurl = accessurl;
    }

    public String getOrgcode() {
        return orgcode;
    }

    public void setOrgcode(String orgcode) {
        this.orgcode = orgcode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getAccessreturn() {
        return accessreturn;
    }

    public void setAccessreturn(String accessreturn) {
        this.accessreturn = accessreturn;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTbsgip() {
        return tbsgip;
    }

    public void setTbsgip(String tbsgip) {
        this.tbsgip = tbsgip;
    }

    public String getProxycn() {
        return proxycn;
    }

    public void setProxycn(String proxycn) {
        this.proxycn = proxycn;
    }



    public String getTime() {
        if (time.endsWith("\""))
            time = time.substring(0, time.length() - 1);
        if (time.startsWith("\"")) {
            time = time.substring(1);
        }
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }

    public String getUpbytes() {
        return upbytes;
    }

    public void setUpbytes(String upbytes) {
        this.upbytes = upbytes;
    }

    public String getServiceid() {
        return serviceid;
    }

    public void setServiceid(String serviceid) {
        this.serviceid = serviceid;
    }


   /* public static void main(String[] args) {
        BigInteger number = new BigInteger("2008123456456413215648748545123156465");

        System.out.println("Number      = " + number);
        System.out.println("Binary      = " + number.toString(2));
        System.out.println("Octal       = " + number.toString(8));
        System.out.println("Hexadecimal = " + number.toString(16));

        number = new BigInteger("FF", 16);
        System.out.println("Number      = " + number);
        System.out.println("Number      = " + number.toString(16));

    }*/


    public void process(String log) {
        this.log = log;
        if (validate(log)) {
            String[] keyvalues = log.split(S_Separator_Keys);
            for (int i = 0; i < keyvalues.length; i++) {
                if (keyvalues[i].startsWith("deviceid=")) {
                    equid = keyvalues[i].substring("deviceid=".length());
                }
                if (keyvalues[i].startsWith("userip=")) {
                    ip = keyvalues[i].substring("userip=".length());
                    if (ip.endsWith("" +
                            "\""))
                        ip = ip.substring(0, ip.length() - 1);
                    if (ip.startsWith("\"")) {
                        ip = ip.substring(1);
                    }

                }
                if (keyvalues[i].startsWith("tbsgip=")) {
                    vpnip = keyvalues[i].substring("tbsgip=".length());
                    if (vpnip.endsWith("" +
                            "\""))
                        vpnip = vpnip.substring(0, vpnip.length() - 1);
                    if (vpnip.startsWith("\"")) {
                        vpnip = vpnip.substring(1);
                    }
                }
                if (keyvalues[i].startsWith("logflag=")) {
                    logflag = keyvalues[i].substring("logflag=".length());
                    if (logflag.endsWith("" +
                            "\""))
                        logflag = logflag.substring(0, logflag.length() - 1);
                    if (logflag.startsWith("\"")) {
                        logflag = logflag.substring(1);
                    }
                }
                if (keyvalues[i].startsWith("on=")) {
                    on = keyvalues[i].substring("on=".length());
                    if (on.endsWith("" +
                            "\""))
                        on = on.substring(0, on.length() - 1);
                    if (on.startsWith("\"")) {
                        on = on.substring(1);
                    }
                }
                if (keyvalues[i].startsWith("serial=")) {
                    serial = keyvalues[i].substring("serial=".length());
                    if (serial.endsWith("" +
                            "\""))
                        serial = serial.substring(0, serial.length() - 1);
                    if (serial.startsWith("\"")) {
                        serial = serial.substring(1);
                        try{
                            BigInteger number = new BigInteger(serial);
                            serial= number.toString(16).toUpperCase();
                        }catch (Exception e){

                        }
                    }
                }

                if (keyvalues[i].startsWith("userip=")) {
                    userip = keyvalues[i].substring("userip=".length());
                    if (userip.endsWith("" +
                            "\""))
                        userip = userip.substring(0, userip.length() - 1);
                    if (userip.startsWith("\"")) {
                        userip = userip.substring(1);
                    }
                }
                if (keyvalues[i].startsWith("accessurl=")) {
                    accessurl = keyvalues[i].substring("accessurl=".length());
                    if (accessurl.endsWith("" +
                            "\""))
                        accessurl = accessurl.substring(0, accessurl.length() - 1);
                    if (accessurl.startsWith("\"")) {
                        accessurl = accessurl.substring(1);
                    }
                }
                if (keyvalues[i].startsWith("orgcode=")) {
                    orgcode = keyvalues[i].substring("orgcode=".length());
                    if (orgcode.endsWith("" +
                            "\""))
                        orgcode = orgcode.substring(0, orgcode.length() - 1);
                    if (orgcode.startsWith("\"")) {
                        orgcode = orgcode.substring(1);
                    }
                }
                if (keyvalues[i].startsWith("username=")) {
                    username = keyvalues[i].substring("username=".length());
                    if (username.endsWith("" +
                            "\""))
                        username = username.substring(0, username.length() - 1);
                    if (username.startsWith("\"")) {
                        username = username.substring(1);
                        try {
                            username = new String(username.getBytes("gbk"),"utf-8");
                        } catch (UnsupportedEncodingException e) {

                        }
                    }
                }
                if (keyvalues[i].startsWith("identity=")) {
                    identity = keyvalues[i].substring("identity=".length());
                    if (identity.endsWith("" +
                            "\""))
                        identity = identity.substring(0, identity.length() - 1);
                    if (identity.startsWith("\"")) {
                        identity = identity.substring(1);
                        try {
                            identity = new String(identity.getBytes("gbk"),"utf-8");
                        } catch (UnsupportedEncodingException e) {

                        }
                    }

                }
                if (keyvalues[i].startsWith("accessreturn=")) {
                    accessreturn = keyvalues[i].substring("accessreturn="
                            .length());
                    if (accessreturn.endsWith("" +
                            "\""))
                        accessreturn = accessreturn.substring(0, accessreturn.length() - 1);
                    if (accessreturn.startsWith("\"")) {
                        accessreturn = accessreturn.substring(1);
                    }
                }
                if (keyvalues[i].startsWith("reason=")) {
                    reason = keyvalues[i].substring("reason=".length());
                    if (reason.endsWith("" +
                            "\""))
                        reason = reason.substring(0, reason.length() - 1);
                    if (reason.startsWith("\"")) {
                        reason = reason.substring(1);
                    }
                }
                if (keyvalues[i].startsWith("tbsgip=")) {
                    tbsgip = keyvalues[i].substring("tbsgip=".length());
                    if (tbsgip.endsWith("" +
                            "\""))
                        tbsgip = tbsgip.substring(0, tbsgip.length() - 1);
                    if (tbsgip.startsWith("\"")) {
                        tbsgip = tbsgip.substring(1);
                    }
                }
                if (keyvalues[i].startsWith("proxycn=")) {
                    proxycn = keyvalues[i].substring("proxycn=".length());
                    if (proxycn.endsWith("" +
                            "\""))
                        proxycn = proxycn.substring(0, proxycn.length() - 1);
                    if (proxycn.startsWith("\"")) {
                        proxycn = proxycn.substring(1);
                        try {
                            proxycn = new String(proxycn.getBytes("gbk"),"utf-8");
                        } catch (UnsupportedEncodingException e) {

                        }
                    }
                }
                if (keyvalues[i].startsWith("terminalid=")) {
                    terminalid = keyvalues[i].substring("terminalid=".length());
                    if (terminalid.endsWith("" +
                            "\""))
                        terminalid = terminalid.substring(0, terminalid.length() - 1);
                    if (terminalid.startsWith("\"")) {
                        terminalid = terminalid.substring(1);
                    }
                }
                if (keyvalues[i].startsWith("time=")) {

                    time = (keyvalues[i].substring("time=".length()) + " " + keyvalues[i + 1]);
                    i++;
                    if (time.endsWith("" +
                            "\""))
                        time = time.substring(0, time.length() - 1);
                    if (time.startsWith("\"")) {
                        time = time.substring(1);
                    }
                }
                if (keyvalues[i].startsWith("bytes=")) {
                    bytes = keyvalues[i].substring("bytes="
                            .length());
                    if (bytes.endsWith("" +
                            "\""))
                        bytes = bytes.substring(0, bytes.length() - 1);
                    if (bytes.startsWith("\"")) {
                        bytes = bytes.substring(1);
                    }

                }
                if (keyvalues[i].startsWith("upbytes=")) {
                    upbytes = keyvalues[i].substring("upbytes="
                            .length());
                    if (upbytes.endsWith("" +
                            "\""))
                        upbytes = upbytes.substring(0, upbytes.length() - 1);
                    if (upbytes.startsWith("\"")) {
                        upbytes = upbytes.substring(1);
                    }
                }
                if (keyvalues[i].startsWith("serviceid=")) {
                    serviceid = keyvalues[i].substring("serviceid=".length());
                    if (serviceid.endsWith("" +
                            "\""))
                        serviceid = serviceid.substring(0, serviceid.length() - 1);
                    if (serviceid.startsWith("\"")) {
                        serviceid = serviceid.substring(1);
                    }
                }
            }
        }

    }

    public void process(String log, String level) {
        this.level = level;
        process(log);
    }


    public boolean validate(String log) {
        boolean result = false;
        if (StringUtils.containsIgnoreCase(log, S_Logflag)
                && StringUtils.containsIgnoreCase(log, S_ServiceID))
            result = true;
        return result;
    }


    public long getIn_Flux() {
        if (upbytes != null && upbytes.equalsIgnoreCase(""))
            return Long.parseLong(upbytes);
        return 0;
    }

    public long getOut_Flux() {
        if (bytes != null && bytes.equalsIgnoreCase(""))
            return Long.parseLong(bytes);
        return 0;
    }




/*    public static void main(String arg[]) throws Exception {

        // String logs = "deviceid=vpn ip=192.168.20.20 logflag=\"TBSGS\" userip=\"171.168.1.1\" accessurl=\"http://192.168.30.30/\" orgcode=\"-\" username=\"????1\" identity=\"123456789012345678\" accessreturn=\"Y\" reason=\"?????????????\" tbsgip=\"192.168.20.20\" proxycn=\"-\" terminalid=\"-\" time=\"2012-04-12 11:49:27\" bytes=\"0\" upbytes=\"0\" serviceid=\"3\" ";
        String logs="logflag=\"TBSGS\" userip=\"192.168.1.200\" accessurl=\"-\" orgcode=\"-\" username=\"��ĳĳ12345123451234\" identity=\"123456123456123456\" accessreturn=\"Y\" reason=\"logout success\" tbsgip=\"192.168.1.1\" proxycn=\"-\" terminalid=\"-\" time=\"2012-07-30 15:16:03\" bytes=\"0\" upbytes=\"0\" serviceid=\"3\"";
        OpenVpnLog log = new OpenVpnLog();
        log.process(logs, "info");

        System.out.println("logflag=" + log.getLogflag() + " time="
                + log.getTime());

    }*/

}
