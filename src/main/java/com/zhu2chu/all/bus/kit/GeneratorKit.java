package com.zhu2chu.all.bus.kit;

/**
 * 2017年8月8日 10:25:55
 * 生成器工具。用于生成一切你想生成的东西，其实很水
 * 
 * @author ThreeX
 *
 */
public class GeneratorKit {

    /**
     * 可以使用此工具获取一个snowflakeId，使用默认的workerId和datacenterId
     * @return 一个snowflakeId，用于分布式UUID。返回值类型为Long
     */
    public static long snowflakeId() {
        return SnowflakeIdWorker.me().nextId();
    }

    /**
     * 可以使用此工具获取一个snowflakeId，使用默认的workerId和datacenterId
     * @return 一个snowflakeId，用于分布式UUID。返回值类型为String
     */
    public static String snowflakeIdForString() {
        return Long.toString(snowflakeId());
    }

}
