package com.zhu2chu.all.bus.kit;

import java.io.File;
import java.net.URISyntaxException;

/**
 * 2017年4月2日 22:40:36
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class PathKit {

    /**
     * 获取windows系统的桌面目录路径
     * 
     * @return
     */
    public static String getWinDeskTopDirectory() {
        String userHome = System.getProperty("user.home");
        String s = userHome + File.separator + "desktop" + File.separator;

        return s;
    }

    /**
     * 2017年8月4日 22:35:12
     * 获取当前项目绝对路径
     * @return
     */
    public static String getUserDir() {
        return System.getProperty("user.dir") + File.separator;
    }

    /**
     * 2017年8月4日 22:52:05
     * 获取当前文件所在绝对路径。精确到包
     * 
     * @param clazz
     * @return
     */
    public static String getCurrentDirectory(Class<?> clazz) {
        File f;
        try {
            f = new File(clazz.getResource("").toURI().getPath());
            return f.getAbsolutePath() + File.separator;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } 

        return null;
    }

}
