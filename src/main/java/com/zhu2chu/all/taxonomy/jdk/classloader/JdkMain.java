package com.zhu2chu.all.taxonomy.jdk.classloader;

import java.io.File;
import java.net.URISyntaxException;

import com.jfinal.kit.PathKit;

/**
 * 2017年7月2日 23:19:31
 * 测试文件路径
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class JdkMain {

    public static void main(String[] args) {
        String classes = PathKit.getRootClassPath();
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
            System.out.println(new File(path).getAbsolutePath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(classes);
    }

}
