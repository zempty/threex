package wang.threex.core;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PropKit;
import com.jfplugin.mail.MailPlugin;

public class ThreeXConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants c) {
		c.setDevMode(true);
	}

	@Override
	public void configRoute(Routes r) {
		r.add("/", IndexController.class);
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
	}

}
