package com.zhu2chu.all.bus.log;

import com.jfinal.log.ILogFactory;
import com.jfinal.log.Log;

public class Log4j2LogFactory implements ILogFactory {

    public Log getLog(Class<?> clazz) {
        return new Log4j2Log(clazz);
    }
    
    public Log getLog(String name) {
        return new Log4j2Log(name);
    }

}
