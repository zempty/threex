package com.zhu2chu.all.core;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.jfinal.plugin.druid.IDruidStatViewAuth;
import com.jfinal.template.Engine;
import com.jfplugin.mail.MailPlugin;
import com.zhu2chu.all.common.FrontRoutes;
import com.zhu2chu.all.common.handler.ConstsHandler;
import com.zhu2chu.all.common.handler.DruidStatViewHandler;
import com.zhu2chu.all.common.router.RouterKit;
import com.zhu2chu.all.test.websocket.WebSocketController;

public class AllConfig extends JFinalConfig {

    private static final Log log = Log.getLog(AllConfig.class);

    private static Prop p = loadGlobalConfig();

	@Override
	public void configConstant(Constants c) {
		c.setDevMode(p.getBoolean("devMode", false));
	}

	@Override
	public void configRoute(Routes r) {
		r.add("/test/websocket", WebSocketController.class);
		RouterKit.scanController(r.getClass(), r);

		List<Route> centerRoutes = r.getRouteItemList();
		if (p.getBoolean("devMode")) {//如果是开发模式，我们就打印中心路由的添加信息
		    for (Route rt : centerRoutes) {
		        Class<? extends Controller> ctlClass = rt.getControllerClass();
		        if (log.isDebugEnabled()) {
		            System.out.println("Config.Routes >>> 添加了Controller：" + ctlClass.getCanonicalName());
                }
		    }
		}

		r.add(new FrontRoutes());
	}

	@Override
	public void configPlugin(Plugins me) {
		me.add(new MailPlugin(PropKit.use("mail.properties").getProperties()));
		
		/**
		 * 事务等级从上(低)往下(高)
		 * 总结如下
		 * 读未提交。无限制，能实时看到数据变化。问题：脏读。读的都是不确定的数据。
		 * 读已提交。只能读已提交的数据。问题：不可重复读。读着读着，数据被人黑了并提交，也能看到。
		 * 可重复读。事务开启前读到的已有数据不会改变。问题：幻读。事务提交前，已有的数据不会看到改变，但此时有人插入了。也能感受到
		 * 序列化。事务会依次执行。
		 *       此级别的事务开启后对某数据进行操作未提交，其它事务只能对其进行select操作。任何更新操作都将阻塞。
		 *       若其它事务对某数据更新后未提交，此级别事务任何对相同数据的操作(包括select)都将阻塞。
		 * 
		 * 使用越低的事务级别开启一个事务。说明读的权限越大，也越不靠谱。
		 * 但无论可种事务级别，一个事务都无法更新其它事务已经操作但未提交的数据。
		 */
	}

	@Override
	public void configInterceptor(Interceptors me) {
		
	}

	@Override
	public void configHandler(Handlers h) {
		h.add(new AllHandler());
		h.add(new ConstsHandler());
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
        //模板引擎开发模式。当在生产环境的时候，设置模板引擎为开发模式，可以在不重启应用的情况下刷新页面。
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

        ActionReporter.setReportAfterInvocation(true);//设置为调用Interceptor前打印log

    }

}
