package com.zhu2chu.all.core;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.ActionReporter;
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

		r.add(new FrontRoutes());
	}

	@Override
	public void configPlugin(Plugins me) {
		me.add(new MailPlugin(PropKit.use("mail.properties").getProperties()));
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
