package com.zhu2chu.all.core;

import com.jfinal.core.Controller;
import com.zhu2chu.all.bus.router.UrlMapping;
import com.zhu2chu.all.front.FrontRoutes;

@UrlMapping(url="/", routeClass={FrontRoutes.class})
public class IndexController extends Controller {
	
	public void index() {
		//MailKit.send("super@qq.com", new ArrayList<String>(), "这是一种传说中的技术", "你学会了吗？");
		render("dragonball/hAdmin/index.html");
	}

}
