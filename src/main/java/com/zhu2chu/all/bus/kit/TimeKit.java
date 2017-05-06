package com.zhu2chu.all.bus.kit;

public class TimeKit {

    /**
     * 计算两个毫秒数之间的相差。以xx时xx分xx秒的形式显示
     * 
     * @param startMillis
     * @param endMillis
     * @return
     */
    public static String calcTime(long startMillis, long endMillis) {
        if (startMillis > endMillis) {
            System.out.println("开始时间比结束时间大");
            return "开始时间比结束时间大";
        }
        if (startMillis == endMillis) {
            return "0秒";
        }

        long lt = endMillis - startMillis;

        long day = lt / (24 * 60 * 60 * 1000);
        long hour = (lt / (60 * 60 * 1000) - day * 24);
        long min = ((lt / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (lt / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        StringBuilder msg = new StringBuilder("消耗时间：");

        if (hour > 0L) {
            msg.append(hour + "时");
        }
        if (min > 0L) {
            msg.append(min + "分");
        }
        if (s > 0L) {
            msg.append(s + "秒");
        }
        if (hour > 0L || min > 0L || s > 0L) {
            msg.append("。");
        }

        msg.append("共" + lt + "毫秒");

        return msg.toString();
    }

}
