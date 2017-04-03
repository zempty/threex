package com.zhu2chu.all.common.kit;

import java.io.File;

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
     * @return
     */
    public static String getWinDeskTopDirectory() {
        String userHome = System.getProperty("user.home");
        String s = userHome + File.separator + "desktop" + File.separator;

        return s;
    }

}
