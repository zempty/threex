package com.zhu2chu.all.taxonomy.jdk.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 2017年8月5日 12:09:49
 * 测试序列化对象与反序列化对象
 * 
 * @author ThreeX
 *
 */
public class Runner {

    public static void main(String[] args) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
            Wife wife1 = new Wife();
            wife1.setName("大处处");
            wife1.setAge(21);
            oos.writeObject(wife1);
            File readWife = new File("tempFile");
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(readWife));
            Wife wife2 = (Wife) ois.readObject();
            System.out.println(wife2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
