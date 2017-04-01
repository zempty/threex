package com.zhu2chu.all.test.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jfinal.log.Log;
import com.zhu2chu.all.common.kit.FileKit;
import com.zhu2chu.all.common.kit.SystemKit;

public class FileMain {

    private static final Log log = Log.getLog(FileMain.class);

    public static void main(String[] args) {
        String filePath = System.getProperty("user.home");
        File srcFile = new File(filePath+File.separator+"origin.rar");
        File destFile = new File(filePath+File.separator+"bakbak.rar");

        String srcFilename = filePath+File.separator+"desktop"+File.separator+"c10959_mb_pin_definition_manual_cn.pdf";
        String destFilename = filePath+File.separator+"desktop"+File.separator+"sss.pdf";

        FileKit.copyFile(srcFilename, destFilename, false);
    }

}
