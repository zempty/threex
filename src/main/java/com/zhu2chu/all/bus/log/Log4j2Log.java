package com.zhu2chu.all.bus.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import com.jfinal.log.Log;

/**
 * 2017年9月23日 00:23:25
 * log4j2 仿制log4j的实现
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class Log4j2Log extends Log {

    private org.apache.logging.log4j.Logger log;
    private static final String callerFQCN = Log4j2Log.class.getName();
    
    Log4j2Log(Class<?> clazz) {
        log = LogManager.getLogger(clazz);
    }
    
    Log4j2Log(String name) {
        log = LogManager.getLogger(name);
    }
    
    public static Log4j2Log getLog(Class<?> clazz) {
        return new Log4j2Log(clazz);
    }
    
    public static Log4j2Log getLog(String name) {
        return new Log4j2Log(name);
    }
    
    public void info(String message) {
        log.log(Level.INFO, message);
    }
    
    public void info(String message, Throwable t) {
        log.log(Level.INFO, message, t);
    }
    
    public void debug(String message) {
        log.log(Level.DEBUG, message);
    }
    
    public void debug(String message, Throwable t) {
        log.log(Level.DEBUG, message, t);
    }
    
    public void warn(String message) {
        log.log(Level.WARN, message);
    }
    
    public void warn(String message, Throwable t) {
        log.log(Level.WARN, message, t);
    }
    
    public void error(String message) {
        log.log(Level.ERROR, message);
    }
    
    public void error(String message, Throwable t) {
        log.log(Level.ERROR, message, t);
    }
    
    public void fatal(String message) {
        log.log(Level.FATAL, message);
    }
    
    public void fatal(String message, Throwable t) {
        log.log(Level.FATAL, message, t);
    }
    
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }
    
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }
    
    public boolean isWarnEnabled() {
        return log.isEnabled(Level.WARN);
    }
    
    public boolean isErrorEnabled() {
        return log.isEnabled(Level.ERROR);
    }
    
    public boolean isFatalEnabled() {
        return log.isEnabled(Level.FATAL);
    }

}
