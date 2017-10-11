package com.zhu2chu.all.bus.log4j2.appender.websocket;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

/**
 * 消息appender。用于获取log4j输出的消息以用在别的地方
 * @Plugin   name属性就是在log4j2.xml 配置的节点名字
 * 
 * @author ThreeX
 *
 */
@Plugin(name="WebPage",category="Core",elementType="appender",printObject=true)
public class WebPageAppender extends AbstractAppender {

    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();

    /**
     * 需要实现的构造方法，直接使用父类就行
     * 
     * @param name
     * @param filter
     * @param layout
     * @param ignoreExceptions
     */
    protected WebPageAppender(String name, Filter filter, Layout<? extends Serializable> layout,
            boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    @Override
    public void append(LogEvent event) {
        //上锁
        readLock.lock();
        try {
            //一个日志消息的字节数组，这里就获取到了消息，接下来想怎么干事都得
            byte[] bytes = getLayout().toByteArray(event);
            //下面这个是要实现的自定义逻辑
            WebPageAsyncWriter.write(new String(bytes));
        } catch (Exception e) {
            if (!ignoreExceptions()) {//如果不忽略异常则抛出去
                throw new AppenderLoggingException(e);
            }
        } finally {
            //必须释放锁头
            readLock.unlock();
        }
    }

    /**
     * @PluginFactory 此注解标识创建插件(appender)的工厂
     * 形参接收配置文件中的参数信息
     * log4j初始化时会根据这个方法创建appender并创建配置文件中配置的参数传进来
     * 
     * @return
     */
    @PluginFactory
    public static WebPageAppender createAppender(@PluginAttribute("name") String name,
            @PluginElement("Filter") final Filter filter,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginAttribute("ignoreExceptions") boolean ignoreExceptions) {
        if (name == null) {
            LOGGER.error("No name provided for MyCustomAppenderImpl");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }

        return new WebPageAppender(name, filter, layout, ignoreExceptions);
    }

}
