package com.zhu2chu.all.taxonomy.jdk.inherit;

public class Son extends Parent {

    private String parent = null;

    public void appson() {
        System.out.println(this.parent);
    }

    /**
     * 无论如何，this都是Son的实例，只是看调用的方法在哪定义的，它所指向的变量就不一样。
     * 如app方法在子类可以访问，是在父类定义的，所以this.parent指向的是父类中定义的那个变量
     * 在子类中调用时，this.parent指向的则是子类中定义的变量。
     * 继承并非是将父类的东西变成自己的(跟在自己内部定义一样的效果)，而是实例一个父类对象，将其括起来而已
     * 总之，访问修饰符控制的只是成员能被访问的位置，它原来属于谁，依旧属于谁。
     */
    /*public static void main(String[] args) {
        Son s = new Son();
        s.app();
        s.appson();
    }*/
}
