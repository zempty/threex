package com.zhu2chu.common.kit;

import java.lang.reflect.Field;

public class InvadeKit {

    public static void main(String[] args) {
        modifyVarValue();
    }

    /**
     * 修改变量的值。不管其是否使用final修饰，只要尝试改变它的引用，对新引用操作都不会影响到原来的值了。
     */
    private static void modifyVarValue() {
        try {
            Class<?> cc = Class.forName("com.jfinal.captcha.CaptchaRender");

            Field f = cc.getDeclaredField("charArray");//还有个getField(xx)的方法，它用于获取public修饰的变量
            f.setAccessible(true);
            char[] s = (char[]) f.get(null);
            char[] s2 = "3456789abcdefghjkmnpqrstuvwxy".toCharArray();
            for (int i=7; i<s.length; i++) {
                s[i] = s2[i];
            }

            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}