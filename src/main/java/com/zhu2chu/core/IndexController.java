package com.zhu2chu.core;

import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.zhu2chu.common.FrontRoutes;
import com.zhu2chu.common.router.UrlMapping;

@UrlMapping(url="/", routeClass={FrontRoutes.class, Routes.class})
public class IndexController extends Controller {
	
	public void index() {
		//MailKit.send("super@qq.com", new ArrayList<String>(), "这是一种传说中的技术", "你学会了吗？");
		render("index.html");
	}

}
