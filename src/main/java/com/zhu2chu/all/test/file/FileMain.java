package com.zhu2chu.all.test.file;

import com.jfinal.log.Log;

public class FileMain {

    private static final Log log = Log.getLog(FileMain.class);

    public static void main(String[] args) {
        String filePath = System.getProperty("user.home");

        /*String srcFilename = "D:\\迅雷下载" + File.separator + "360cse_8.7.0.306.exe";
        String srcFilename2 = "D:\\迅雷下载" + File.separator + "360cse_8.7.0.306base64.txt";
        String srcFilename3 = filePath + File.separator + "desktop" + File.separator + "videobase64.mp4";

        FileKit.writeBase64(srcFilename, srcFilename2, 1024);*/
        // FileKit.readBase64(srcFilename2, srcFilename3);

        /*File file = new File("D:\\360Downloads\\wu_1bpo6fihe16jd1jlr1eo1jc1k30ta");
        File[] array = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return false;
                }
                return true;
            }
        });
        System.out.println(array);

        String s = "D:\\360Downloads\\"+"\u0000"+"wu_1bpo6fihe16jd1jlr1eo1jc1k30ta";
        //FileKit.nioTransferCopy(srcFilename, srcFilename3);
        System.out.println(s.indexOf("\u0000")<0);
        System.out.println("file.exists:"+file.exists());*/

        System.out.println();
    }

}
