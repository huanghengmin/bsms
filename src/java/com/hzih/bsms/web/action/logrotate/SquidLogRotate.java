package com.hzih.bsms.web.action.logrotate;

import com.hzih.bsms.syslog.SysLogSend;
import com.hzih.bsms.utils.StringContext;
import com.inetec.common.util.OSInfo;
import com.inetec.common.util.Proc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hhm on 2014/12/10.
 * squid access.log parse
 */
public class SquidLogRotate {

//    final String command = "/usr/local/squid/sbin/squid -k rotate";

//    final String log_file = "/usr/local/squid/var/logs/access.log";


    public SquidLogObj parseLine(String line) {
        if(line!=null&&line.length()>0){
            String[] strings = line.split("\\|");
            if(strings!=null&&strings.length==7){
                SquidLogObj squidLogObj = new SquidLogObj();
                squidLogObj.setDate(strings[0]);
                squidLogObj.setHost(strings[1]);
                squidLogObj.setResult_code(strings[2]);
                squidLogObj.setRequest_bytes(strings[3]);
                squidLogObj.setReply_bytes(strings[4]);
                squidLogObj.setRequest_msg(strings[5]);
                squidLogObj.setClient_msg(strings[6]);
                return squidLogObj;
            }
        }
        return null;
    }


    public List<SquidLogObj> readLogObjs(File file)  {
        if (file.exists()) {
            FileReader fr = null;
            BufferedReader br = null;
            try {
                try {
                    fr = new FileReader(file);// 建立FileReader对象，并实例化为fr
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                br = new BufferedReader(fr);// 建立BufferedReader对象，并实例化为br
                String line = br.readLine();// 从文件读取一行字符串
                if (line != null) {
                    List<SquidLogObj> squidLogObjs = new ArrayList<>();
                    SquidLogObj squidLogObj = parseLine(line);
                    if (squidLogObj != null) {
                        squidLogObjs.add(squidLogObj);
                    }
                    while (line != null) {      // 判断读取到的字符串是否不为空
                        line = br.readLine();// 从文件中继续读取一行数据
                        if (line != null) {
                            SquidLogObj squidLogObj_while = parseLine(line);
                            if (squidLogObj_while != null)
                                squidLogObjs.add(squidLogObj_while);
                        }
                    }
                    return squidLogObjs;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null)
                        br.close();// 关闭BufferedReader对象
                    if (fr != null)
                        fr.close();// 关闭文件
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void rotateSquidLog(){
        Proc proc = new Proc();
        String command = null;
        if (OSInfo.getOSInfo().isWin()) {
            command = "sh " + StringContext.squidPath+"/sbin/squid -k rotate";
        } else {
            command = "sh " + StringContext.squidPath+"/sbin/squid -k rotate";
        }
        proc.exec(command);
    }


   /* public static void main(String args[])throws Exception{
        SquidLogRotate squidLogRotate = new SquidLogRotate();
        List<SquidLogObj> squidLogObjs = squidLogRotate.readLogObjs("D:/access.log");
        if(squidLogObjs!=null) {
            for (SquidLogObj s : squidLogObjs) {
                System.out.println(s.getDate());
            }
        }
    }*/
}
