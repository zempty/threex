package com.zhu2chu.all.test.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
<<<<<<< HEAD
=======

import org.apache.commons.codec.binary.Base64;
>>>>>>> remotes/origin/master

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

<<<<<<< HEAD
        FileKit.copyFile(srcFilename, destFilename, false);
=======
            readFileByBytes(filePath+File.separator+"readme.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void createFile(File f, boolean isDelete) {
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFileByBytes(String filename) {
        File f = new File(filename);
        InputStream in = null;
        OutputStream out = null;
        System.out.println("以字节为单位读取文件内容，一次读一个字节：");
        try {
            in = new FileInputStream(f);
            int tempByte;
            /*while ((tempByte=in.read()) != -1) {
                System.out.write(tempByte);
            }*/
            byte[] s = Base64.decodeBase64("5oiR5Lus5LiA5LiL5Zyo6L+Z6YeM5ZWK");
            System.out.println(new String(s, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
>>>>>>> remotes/origin/master
    }

}
