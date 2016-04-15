package com.hzih.bsms.domain;

import java.util.Date;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-24
 * Time: 下午4:46
 * To change this template use File | Settings | File Templates.
 */
public class CaUser {
    private int id;                 //数据库id
    private String cn;              //用户名
    private String user_ip;
    private String vpn_ip;
    private int on;
    private int status;      //授权访问状态
    private String hzihpassword;   //密码
    private String repassword;     //重复密码
    private String hzihid;          //身份证
    private String hzihphone;      //电话
    private String hzihaddress;    //地址
    private String hzihemail;      //邮箱
    private String hzihjobnumber; //警号
    private String phonenetid;    //入网串号
    private String hzihcaserialNumber;     //证书序列号
    private String terminalid;      //终端编号
    //    private String terminal_pwd;    //终端密码
//    private String terminal_pwd_audit;  //终端密码审批
    private String hzihdn;                //ldap数据库DN
    private String hzihprovince ;      //省
    private String hzihcity;            //市
    private String hzihorganization;      //组织
    private String hzihinstitutions;     //机构
    private String hzihcastatus;         //证书发布状态
    private String hzihparentca;        //父ca路径
    private String hzihcavalidity;  //证书有效期
    private String hzihcertificatetype;  //ca发证证书类型
//    private String abnormalFlag;   //违规超限
//    private String abnormalMessage;    //违规消息
//    private String logFlag;     //日志标识
//    private List<AccessUrl> accessUrls;    //访问过的url
//    private String abnormalUrl;   //违规url列表
//    private List<Wifi> wifis;          //打开wifi记录
//    private List<BlueTooth> blueTooths;     //打开蓝牙记录
//    private List<StopList> runProcesses;    //终端运行进程
//    private String abnormalProcess;  //违规进程列表
//    private String wifi_msg;
//    private String booth_msg;
    private int viewFlag = 0;     //截屏
//    private int location = 0;     //上传地理位置
//    private String date;        //日期
//    private String msg;     //违规信息
    private Set<CaRole> caRoles;
    private Date logindate;     //登陆时间
    private Date createdate;    //创建时间
    private Date onlinetime;    //在线时间

    public int getOn() {
        return on;
    }

    public void setOn(int on) {
        this.on = on;
    }

    public String getPhonenetid() {
        return phonenetid;
    }

    public void setPhonenetid(String phonenetid) {
        this.phonenetid = phonenetid;
    }

    public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

  public Set<CaRole> getCaRoles() {
        return caRoles;
    }

    public void setCaRoles(Set<CaRole> caRoles) {
        this.caRoles = caRoles;
    }
    /*
    public String getWifi_msg() {
        return wifi_msg;
    }

    public void setWifi_msg(String wifi_msg) {
        this.wifi_msg = wifi_msg;
    }

    public String getBooth_msg() {
        return booth_msg;
    }

    public void setBooth_msg(String booth_msg) {
        this.booth_msg = booth_msg;
    }

    public String getAbnormalProcess() {
        return abnormalProcess;
    }

    public void setAbnormalProcess(String abnormalProcess) {
        this.abnormalProcess = abnormalProcess;
    }

    public String getAbnormalUrl() {
        return abnormalUrl;
    }

    public void setAbnormalUrl(String abnormalUrl) {
        this.abnormalUrl = abnormalUrl;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }*/

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getVpn_ip() {
        return vpn_ip;
    }

    public void setVpn_ip(String vpn_ip) {
        this.vpn_ip = vpn_ip;
    }

/*    public String getLocationstr() {

        return locationstr;
    }

    public void setLocationstr(String locationstr) {
        this.locationstr = locationstr;
    }*/

//    private String locationstr="";     //上传地理位置

    public CaUser() {
    }

    public CaUser(int id) {

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getHzihcavalidity() {
        return hzihcavalidity;
    }

    public void setHzihcavalidity(String hzihcavalidity) {
        this.hzihcavalidity = hzihcavalidity;
    }

    public String getHzihpassword() {
        return hzihpassword;
    }

    public void setHzihpassword(String hzihpassword) {
        this.hzihpassword = hzihpassword;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getHzihid() {
        return hzihid;
    }

    public void setHzihid(String hzihid) {
        this.hzihid = hzihid;
    }

    public String getHzihphone() {
        return hzihphone;
    }

    public void setHzihphone(String hzihphone) {
        this.hzihphone = hzihphone;
    }

    public String getHzihaddress() {
        return hzihaddress;
    }

    public void setHzihaddress(String hzihaddress) {
        this.hzihaddress = hzihaddress;
    }

    public String getHzihemail() {
        return hzihemail;
    }

    public void setHzihemail(String hzihemail) {
        this.hzihemail = hzihemail;
    }

    public String getHzihjobnumber() {
        return hzihjobnumber;
    }

    public void setHzihjobnumber(String hzihjobnumber) {
        this.hzihjobnumber = hzihjobnumber;
    }

/*    public String getPhonenetid() {
        return phonenetid;
    }

    public void setPhonenetid(String phonenetid) {
        this.phonenetid = phonenetid;
    }*/

    public String getHzihcaserialNumber() {
        return hzihcaserialNumber;
    }

    public void setHzihcaserialNumber(String hzihcaserialNumber) {
        this.hzihcaserialNumber = hzihcaserialNumber;
    }

   /* public String getTerminalid() {
        return terminalid;
    }

    public void setTerminalid(String terminalid) {
        this.terminalid = terminalid;
    }

    public String getTerminal_pwd() {
        return terminal_pwd;
    }

    public void setTerminal_pwd(String terminal_pwd) {
        this.terminal_pwd = terminal_pwd;
    }

    public String getTerminal_pwd_audit() {
        return terminal_pwd_audit;
    }

    public void setTerminal_pwd_audit(String terminal_pwd_audit) {
        this.terminal_pwd_audit = terminal_pwd_audit;
    }
*/
    public String getHzihdn() {
        return hzihdn;
    }

    public void setHzihdn(String hzihdn) {
        this.hzihdn = hzihdn;
    }

    public String getHzihprovince() {
        return hzihprovince;
    }

    public void setHzihprovince(String hzihprovince) {
        this.hzihprovince = hzihprovince;
    }

    public String getHzihcity() {
        return hzihcity;
    }

    public void setHzihcity(String hzihcity) {
        this.hzihcity = hzihcity;
    }

    public String getHzihorganization() {
        return hzihorganization;
    }

    public void setHzihorganization(String hzihorganization) {
        this.hzihorganization = hzihorganization;
    }

    public String getHzihinstitutions() {
        return hzihinstitutions;
    }

    public void setHzihinstitutions(String hzihinstitutions) {
        this.hzihinstitutions = hzihinstitutions;
    }

    public String getHzihcastatus() {
        return hzihcastatus;
    }

    public void setHzihcastatus(String hzihcastatus) {
        this.hzihcastatus = hzihcastatus;
    }

    public String getHzihparentca() {
        return hzihparentca;
    }

    public void setHzihparentca(String hzihparentca) {
        this.hzihparentca = hzihparentca;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHzihcertificatetype() {
        return hzihcertificatetype;
    }

    public void setHzihcertificatetype(String hzihcertificatetype) {
        this.hzihcertificatetype = hzihcertificatetype;
    }

  /*  public String getAbnormalFlag() {
        return abnormalFlag;
    }

    public void setAbnormalFlag(String abnormalFlag) {
        this.abnormalFlag = abnormalFlag;
    }

    public String getAbnormalMessage() {
        return abnormalMessage;
    }

    public void setAbnormalMessage(String abnormalMessage) {
        this.abnormalMessage = abnormalMessage;
    }

    public String getLogFlag() {
        return logFlag;
    }

    public void setLogFlag(String logFlag) {
        this.logFlag = logFlag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<AccessUrl> getAccessUrls() {
        return accessUrls;
    }

    public void setAccessUrls(List<AccessUrl> accessUrls) {
        this.accessUrls = accessUrls;
    }

    public List<Wifi> getWifis() {
        return wifis;
    }

    public void setWifis(List<Wifi> wifis) {
        this.wifis = wifis;
    }

    public List<BlueTooth> getBlueTooths() {
        return blueTooths;
    }

    public void setBlueTooths(List<BlueTooth> blueTooths) {
        this.blueTooths = blueTooths;
    }

    public List<StopList> getRunProcesses() {
        return runProcesses;
    }

    public void setRunProcesses(List<StopList> runProcesses) {
        this.runProcesses = runProcesses;
    }*/

    public int getViewFlag() {
        return viewFlag;
    }

    public void setViewFlag(int viewFlag) {
        this.viewFlag = viewFlag;
    }

    public Date getLogindate() {
        return logindate;
    }

    public void setLogindate(Date logindate) {
        this.logindate = logindate;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getOnlinetime() {
        return onlinetime;
    }

    public void setOnlinetime(Date onlinetime) {
        this.onlinetime = onlinetime;
    }
}
