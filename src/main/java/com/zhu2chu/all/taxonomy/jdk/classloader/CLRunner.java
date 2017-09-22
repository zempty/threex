package com.zhu2chu.all.taxonomy.jdk.classloader;

import java.net.URISyntaxException;

/**
 * 2017年7月2日 23:19:31
 * 测试文件路径
 * 总结getClass().getResource和ClassLoader.getResource的区别
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class CLRunner {

    public static void main(String[] args) {
        try {
            System.out.println(Thread.currentThread().getContextClassLoader());

            /*
             * 获取ClassLoader的classpath路径
             * 等同于XX.class.getResource("/")
             */
            String classpath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
            System.out.println(classpath);

            //ClassLoader.getResource不能以/开头，都是从classpath下找。一般用于从classpath根目录开始定位的资源
            String path1 = Thread.currentThread().getContextClassLoader().getResource("com/zhu2chu/all/core/All.class").toURI().getPath();
            System.out.println(path1);

            /*
             * 一般用于从当前类所在的包开始查找资源，此时不以/开头
             * 以/开头就是从当前类的classloader的根目录下查找。
             */
            String classRec = CLRunner.class.getResource("res/1.txt").toURI().getPath();
            System.out.println(classRec);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
