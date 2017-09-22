package com.zhu2chu.all.taxonomy.front.jqueryui;

import com.jfinal.core.Controller;
import com.zhu2chu.all.bus.router.URIMapping;
import com.zhu2chu.all.front.FrontRoutes;

/**
 * 测试ZUI框架相关功能
 * 
 * @author ThreeX
 *
 */
@URIMapping(uri="/jqueryui",routeClass=FrontRoutes.class)
public class JQueryUIController extends Controller {

    public void index() {
        render("jqueryuidemo.html");
    }

}
