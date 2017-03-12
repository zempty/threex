package com.zhu2chu.core;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.UrlSkipHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.druid.IDruidStatViewAuth;
import com.jfinal.template.Engine;
import com.jfplugin.mail.MailPlugin;
import com.zhu2chu.common.handler.DruidStatViewHandler;
import com.zhu2chu.test.TestController;
import com.zhu2chu.test.websocket.WebSocketController;

public class ThreeXConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants c) {
		c.setDevMode(true);
	}

	@Override
	public void configRoute(Routes r) {
		r.add("/", IndexController.class);
		r.add("/test", TestController.class);
		r.add("/test/websocket", WebSocketController.class);
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
		h.add(new ThreeXHandler());
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
        
    }

}
