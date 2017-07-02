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
     * 2017年7月2日 23:30:00
     * 获取classpath的路径，一般我们要的是我们写的类的路径
     * 
     * @return
     */
    public static String getClassesPath() {
        String path;
        try {
            path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
            return new File(path).getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

}
