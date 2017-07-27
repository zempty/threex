package com.zhu2chu.all.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.config.Routes.Route;
import com.jfinal.core.ActionReporter;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.IDruidStatViewAuth;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.template.Engine;
import com.jfplugin.mail.MailPlugin;
import com.mysql.jdbc.Connection;
import com.zhu2chu.all.admin.bus.AdminRoutes;
import com.zhu2chu.all.bus.h2.H2Dialect;
import com.zhu2chu.all.bus.h2.H2ServerHandler;
import com.zhu2chu.all.bus.h2.H2ServerPlugin;
import com.zhu2chu.all.bus.handler.DruidStatViewHandler;
import com.zhu2chu.all.bus.router.RouterKit;
import com.zhu2chu.all.front.FrontRoutes;
import com.zhu2chu.all.test.websocket.WebSocketController;

public class AllConfig extends JFinalConfig {

    private static final Log log = Log.getLog(AllConfig.class);

    private static Prop p = loadGlobalConfig();
    private WallFilter wallFilter;
    private WallConfig wallConfig;

    @Override
    public void configConstant(Constants c) {
        c.setDevMode(p.getBoolean("devMode", false));
    }

    @Override
    public void configRoute(Routes r) {
        r.add("/test/websocket", WebSocketController.class);
        RouterKit.scanController(r.getClass(), r);

        List<Route> centerRoutes = r.getRouteItemList();
        if (p.getBoolean("devMode")) {// 如果是开发模式，我们就打印中心路由的添加信息
            for (Route rt : centerRoutes) {
                Class<? extends Controller> ctlClass = rt.getControllerClass();
                if (log.isDebugEnabled()) {
                    System.out.println("Config.Routes >>> 添加了Controller：" + ctlClass.getCanonicalName());
                }
            }
        }

        r.add(new FrontRoutes());
        r.add(new AdminRoutes());
    }

    @Override
    public void configPlugin(Plugins me) {
        me.add(new MailPlugin(PropKit.use("mail.properties").getProperties()));
        me.add(new EhCachePlugin());

        // 启动h2 tcpserver插件。此插件必须放在ActiveRecordPlugin的前面，不然arp启动时就报错。
        me.add(new H2ServerPlugin());

        /**
         * 事务隔离级别从上(低)往下(高) 总结如下 读未提交。无限制，能实时看到数据变化。问题：脏读。读的都是不确定的数据。
         * 读已提交。只能读已提交的数据。问题：不可重复读。读着读着，数据被人黑了并提交，也能看到。
         * 可重复读。事务开启前读到的已有数据不会改变。问题：幻读。查询后其它事务能对已查数据进行操作提交，
         * 但该事务提交前，已有的数据不会看到改变。包括update、insert。在该事务提交后，才会看到其它 事务更改的变化。
         * 这样引出的问题就是当你commit后，发现数据变了。比如：你金额有1000，然后别人的事务用了500并提交，
         * 你这边查还是1000，当你想用600的时候，然后会发现使用不了。因为你还剩500块，但你查还是1000，会
         * 感到莫名奇妙。只有提交后你才能看到余额为500块。由此可见，任何跟钱有关的事务应该都是使用序列化的隔离级别 序列化。事务会依次执行。
         * 1.此级别的事务开启后对某数据进行操作(包括select)未提交，其它事务只能对相同数据进行select操作。任何更新操作都将阻塞。
         * 2.若其它事务对某数据更新后未提交，此级别事务任何对相同数据的操作(包括select)都将阻塞。
         * 
         * 使用越低的事务隔离级别开启一个事务。说明读的权限越大，也越不靠谱。
         * 但无论何种事务隔离级别，一个事务都无法更新其它事务已经操作(update、delete、insert)但未提交的数据。
         */
        //此处理是为了让配置中配置的数据库类型为带点不带点都可以正确运行。如h2、h2.
        String dbType = p.get("dbtype", "h2");
        String dbTypeWithDot = dbType;//带点的数据库类型前缀
        if (!dbType.contains(".")) {
            dbTypeWithDot = dbType+".";
        } else {
            dbType = dbType.split("\\.")[0];
        }

        String jdbcUrl = p.get(dbTypeWithDot+"jdbcurl").replace("${"+dbTypeWithDot+"tcpPort}", p.get(dbTypeWithDot+"tcpPort"));
        String username = p.get(dbTypeWithDot+"username");
        String password = p.get(dbTypeWithDot+"password");

        DruidPlugin dp1 = new DruidPlugin(jdbcUrl, username, password);
        wallFilter = new WallFilter();
        wallFilter.setDbType(dbType);

        wallConfig = new WallConfig();
        // 设置允许多语句执行。如：sql1;sql2;
        wallConfig.setMultiStatementAllow(true);
        wallFilter.setConfig(wallConfig);

        dp1.addFilter(wallFilter);
        dp1.addFilter(new StatFilter());
        me.add(dp1);

        ActiveRecordPlugin arp1 = new ActiveRecordPlugin(dp1);
        arp1.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
        arp1.setDialect(new H2Dialect());
        me.add(arp1);

        // 开发模式下打印sql
        if (p.getBoolean("devMode", false)) {
            arp1.setShowSql(true);
        }
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers h) {
        h.add(new DruidStatViewHandler("/assets/druid"));
        h.add(new H2ServerHandler());

        h.add(new AllHandler());
        h.add(new UrlSkipHandler("^/websocket.*", false));
        h.add(new DruidStatViewHandler("/db/druid", new IDruidStatViewAuth() {
            @Override
            public boolean isPermitted(HttpServletRequest request) {
                return true;
            }
        }));
    }

    @Override
    public void configEngine(Engine me) {
        // 模板引擎开发模式。当在生产环境的时候，设置模板引擎为开发模式，可以在不重启应用的情况下刷新页面。
        me.setDevMode(p.getBoolean("engDevMode", true));
    }

    public static void main(String[] args) {
        /**
         * 特别注意：Eclipse 之下建议的启动方式
         */
        JFinal.start("src/main/webapp", 8022, "/", 5);

        /**
         * 特别注意：IDEA 之下建议的启动方式，仅比 eclipse 之下少了最后一个参数
         */
        // JFinal.start("src/main/webapp", 80, "/");
    }

    private static Prop loadGlobalConfig() {
        return PropKit.use("global.properties");
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();

        /*
         * 使用此语句需注意。在controller调用前执行reporter的话，会解析request的输入流
         * 之后，再在controller就不能解析了，在tomcat中，输入流只能解析一次。
         */
        ActionReporter.setReportAfterInvocation(false);// 设置为调用Interceptor前打印log

    }

}
