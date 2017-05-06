package com.zhu2chu.all.test.file;

import java.io.File;

import com.jfinal.log.Log;
import com.zhu2chu.all.bus.kit.FileKit;

public class FileMain {

    private static final Log log = Log.getLog(FileMain.class);

    public static void main(String[] args) {
        String filePath = System.getProperty("user.home");

        String srcFilename = filePath + File.separator + "desktop" + File.separator + "ddd.mp4";
        String srcFilename2 = filePath + File.separator + "desktop" + File.separator + "videobase64.txt";
        String srcFilename3 = filePath + File.separator + "desktop" + File.separator + "videobase64.mp4";

        // FileKit.writeBase64(srcFilename, srcFilename2, 1024);
        // FileKit.readBase64(srcFilename2, srcFilename3);

        FileKit.nioTransferCopy(srcFilename, srcFilename3);
    }

}
