package com.zhu2chu.all.bus.kit;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2017年4月18日 18:01:56
 * 此类出自：http://blog.csdn.net/z69183787/article/details/12848685 验证是否数字???
 * 
 * @author ThreeX
 *
 */
public class NumberKit {

    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    public static boolean isPositiveDecimal(String orginal) {
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    public static boolean isNegativeDecimal(String orginal) {
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
    }

    public static boolean isDecimal(String orginal) {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    public static boolean isRealNumber(String orginal) {
        return isWholeNumber(orginal) || isDecimal(orginal);
    }

    ////////////////无符号，有时我们不需要+号来判断正数，即使是+5.6这样的数我们也认为不合法，则使用如下方法
    public static boolean isPositiveIntegerNoSign(String orginal) {
        return isMatch("^[1-9]\\d*", orginal);
    }
    public static boolean isPositiveDecimalNoSign(String orginal) {
        return isMatch("[0]\\.[1-9]*|[1-9]\\d*\\.\\d*", orginal);
    }
    public static boolean isWholeNumberNoSign(String orginal) {
        return isMatch("[-]{0,1}0", orginal) || isPositiveIntegerNoSign(orginal) || isNegativeInteger(orginal);
    }
    public static boolean isDecimalNoSign(String orginal) {
        return isPositiveDecimalNoSign(orginal) || isNegativeDecimal(orginal);
    }
    public static boolean isRealNumberNoSign(String orginal) {
        return isWholeNumberNoSign(orginal) || isDecimalNoSign(orginal);
    }

    /////////////////////////////以下是其它功能
    /**
     * 2017年7月25日 15:33:47
     * 保留指定位数的小数，四舍五入
     * 
     * @param number 数字
     * @param bit 位数
     * @return
     */
    public static double toFixed(double number, int bit) {
        return toFixed(number, bit, true);
    }

    /**
     * 2017年7月25日 15:53:39
     * 保留指定位数的小数，非四舍五入。
     * 
     * @param number
     * @param bit
     * @return
     */
    public static double toFixedDown(double number, int bit) {
        return toFixed(number, bit, false);
    }

    /**
     * 2017年7月25日 15:54:11
     * 返回指定位数的小数
     * 
     * @param number
     * @param bit
     * @param up
     * @return
     */
    public static double toFixed(double number, int bit, boolean up) {
        BigDecimal bg = new BigDecimal(number);
        if (up) {
            return bg.setScale(bit, BigDecimal.ROUND_UP).doubleValue();
        } else {
            return bg.setScale(bit, BigDecimal.ROUND_DOWN).doubleValue();
        }
    }

}
