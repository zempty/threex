package com.zhu2chu.all.bus.kit;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 2017年5月6日 10:50:54
 * System相关工具类
 * 
 * @author ThreeX
 *
 */
public class SystemKit {

    public enum OSType {
        OS_TYPE_LINUX, OS_TYPE_WIN, OS_TYPE_SOLARIS, OS_TYPE_MAC, OS_TYPE_FREEBSD, OS_TYPE_OTHER
    }

    private static OSType getOSType() {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            return OSType.OS_TYPE_WIN;
        } else if (osName.contains("SunOS") || osName.contains("Solaris")) {
            return OSType.OS_TYPE_SOLARIS;
        } else if (osName.contains("Mac")) {
            return OSType.OS_TYPE_MAC;
        } else if (osName.contains("FreeBSD")) {
            return OSType.OS_TYPE_FREEBSD;
        } else if (osName.startsWith("Linux")) {
            return OSType.OS_TYPE_LINUX;
        } else {
            // Some other form of Unix
            return OSType.OS_TYPE_OTHER;
        }
    }

    private static final OSType osType = getOSType();
    // Helper static vars for each platform
    private static final boolean WINDOWS = (osType == OSType.OS_TYPE_WIN);
    private static final boolean SOLARIS = (osType == OSType.OS_TYPE_SOLARIS);
    private static final boolean MAC = (osType == OSType.OS_TYPE_MAC);
    private static final boolean FREEBSD = (osType == OSType.OS_TYPE_FREEBSD);
    private static final boolean LINUX = (osType == OSType.OS_TYPE_LINUX);
    private static final boolean OTHER = (osType == OSType.OS_TYPE_OTHER);

    /**
     * 返回操作系统类型
     * @return
     */
    public static String osType() {
        return osType.toString();
    }

    public static boolean isSolaris() {
        return SOLARIS;
    }
    public static boolean notSolaris() {
        return !SOLARIS;
    }
    public static boolean isMacOS() {
        return MAC;
    }
    public static boolean notMacOS() {
        return !MAC;
    }
    public static boolean isFreeBSD() {
        return FREEBSD;
    }
    public static boolean notFreeBSD() {
        return !FREEBSD;
    }
    public static boolean isLinux() {
        return LINUX;
    }
    public static boolean notLinux() {
        return !LINUX;
    }
    public static boolean isWindows() {
        return WINDOWS;
    }
    public static boolean notWindows() {
        return !WINDOWS;
    }

    /**
     * 输出System中的所有键值对
     */
    public static void outputSysProperties() {
        Properties p = System.getProperties();

        for (Enumeration<?> e = p.propertyNames(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            System.out.println(key + ":" + p.getProperty(key));
        }
    }

    /**
     * jdk自带方法输出
     * 
     * @param bool
     */
    public static void outputSysProperties(boolean bool) {
        System.getProperties().list(System.out);
    }

    /**
     * 获取user.home的路径
     * 
     * @return
     */
    public static String getSysUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * 设置系统代理
     * @param kvs
     */
    public static void setSystemProxy(Properties kvs) {
        System.setProperties(kvs);
    }

    /**
     * 2017年9月21日 14:50:53
     * 调用默认浏览器打开相应的url
     * 下面这种方式。在eclipse打断点后，单步过能弹出浏览器，直接运行过去就不行，不靠谱的
     * process = Runtime.getRuntime().exec("cmd /c start http://127.0.0.1:17878");
     * 
     * @param url
     */
    public static void openBrowse(String url) {
        try {
            // 创建一个URI实例
            java.net.URI uri = java.net.URI.create(url);
            // 获取当前系统桌面扩展
            java.awt.Desktop dp = java.awt.Desktop.getDesktop();
            // 判断系统桌面是否支持要执行的功能
            if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                // 获取系统默认浏览器打开链接 
                dp.browse(uri) ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        outputSysProperties(true);
    }

}
