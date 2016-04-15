package com.hzih.bsms.web.action.logrotate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by hhm on 2014/12/10.
 */
public class SquidFileRegex {

    static class LogFilenameFilter {
        private Pattern p;

        public LogFilenameFilter(String regex) {
            p = Pattern.compile(regex);
        }

        public boolean accept(String name) {
            return p.matcher(name).matches();
        }
    }

    /*public static void main(String args[]) throws Exception {
        List<File> fileList = getFiles("D:/");
        for (File file : fileList) {
            System.out.println(file.getName());
        }
    }*/

    /**
     *   filename access.log
     */
    static List<File> getFiles(String filePath,String filename) {
        List<File> fileList = new ArrayList<>();
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (!file.isDirectory()) {
                LogFilenameFilter logFilenameFilter = new LogFilenameFilter(filename+".\\d+$");
                boolean flag = logFilenameFilter.accept(file.getName());
                if (flag) {
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }
}
