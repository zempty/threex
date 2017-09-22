package com.zhu2chu.all.taxonomy.jdk.inherit;

public class Parent {

    private String parent = "app";

    public void app() {
        if (this.parent == null) {
            System.out.println("parent为空");
        } else {
            System.out.println("parent不为空");
        }
    }

}
