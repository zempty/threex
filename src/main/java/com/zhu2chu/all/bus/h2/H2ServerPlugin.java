package com.zhu2chu.all.bus.h2;

import java.sql.SQLException;

import org.h2.tools.Server;

import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.IPlugin;
import com.zhu2chu.all.bus.kit.PropExtKit;

/**
 * 2017年4月30日 21:43:53 启动h2的server(tcp)模式
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class H2ServerPlugin implements IPlugin {

    private static final Log log = Log.getLog(H2ServerPlugin.class);
    private Server tcpServer;
    private Server webServer;

    /**
     * 是否创建web server?默认为false,不创建
     */
    private boolean createWebServer = false;

    public H2ServerPlugin() {
    }

    /**
     * 是否创建web server?
     * 
     * @param createWeb
     *            true是，false否
     */
    public H2ServerPlugin(boolean createWeb) {
        this.createWebServer = createWeb;
    }

    @Override
    public boolean start() {
        try {
            if (log.isInfoEnabled()) {
                System.out.println("-------------------启动H2数据库开始-------------------");
            }

            if (log.isInfoEnabled()) {
                System.out.println(">>>>>>>>>>>>>使用tcp server模式启动...");
            }
            // 详细的参数见Server.main方法。有空格的项是两个参数来的。如：-baseDir
            // <dir>，要写成："-baseDir","./dbs"
            String baseDir = PropExtKit.get(PropKit.get("h2.baseDir"), "/dbs");
            //baseDir = ClassKit.getClassPath()+baseDir;

            String tcpPort = PropExtKit.get(PropKit.get("h2.tcpPort"), "9123");

            tcpServer = Server.createTcpServer("-tcpPort", tcpPort, "-baseDir", baseDir).start();
            if (log.isInfoEnabled()) {
                System.out.println(">>>>>>>>>>>>>server(tcp)模式启动成功。tcpPort:" + tcpPort);
            }

            // 创建web server，便于使用控制台管理h2数据库
            if (createWebServer) {
                if (log.isInfoEnabled()) {
                    System.out.println(">>>>>>>>>>>>>启动H2数据库web控制台...");
                }
                String webPort = PropExtKit.get(PropKit.get("h2.webPort"), "9122");
                webServer = Server.createWebServer("-trace", "-webPort", webPort, "-baseDir", baseDir).start();
                if (log.isInfoEnabled()) {
                    System.out.println(">>>>>>>>>>>>>H2数据库web控制台启动成功。webPort:" + webPort);
                }
            }

            if (log.isInfoEnabled()) {
                System.out.println("-------------------H2数据库启动完毕-------------------");
            }

            return true;
        } catch (SQLException e) {
            if (log.isInfoEnabled()) {
                System.out.println("启动H2数据库出错：" + e.toString());
            }
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean stop() {
        if (log.isInfoEnabled()) {
            System.out.println("正在停止H2数据库...");
        }
        if (tcpServer != null) {
            tcpServer.stop();
        }
        if (log.isInfoEnabled()) {
            System.out.println("H2数据库停止完毕！");
        }
        return true;
    }

}
