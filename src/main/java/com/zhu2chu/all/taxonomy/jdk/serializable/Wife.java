package com.zhu2chu.all.taxonomy.jdk.serializable;

import java.io.Serializable;

public class Wife implements Serializable {

    /**
     * eclipse生成的序列号。这里有一点要注意，如果在eclipse使用maven，maven有提示错误的话，
     * 是生成不了计算的序列号的，只能生成默认序列号，即1L。所以需要把maven的错误消除先，有时并不
     * 真的是maven的pom.xml有错。也有可能是eclipse误报，需要先注释掉报错的节点，再加回来只要
     * 不再报错就OK了。
     * 
     * 2017年8月5日 11:33:24
     * 答案在此：https://stackoverflow.com/questions/17652439/eclipse-cannot-generate-a-serial-version-id
     * Damo的回答：
     * In my case (I'm using Maven), I had to delete one of the dependencies in the local
     * repository because it had become corrupted. Look for errors about problem reading
     * dependency jars.
     * 
     */
    private static final long serialVersionUID = 1656311114337361757L;

    private String name = "chuchu";
    private int age = 20;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())
               +"{name:"+name+",age="+age+"}";
    }

}
