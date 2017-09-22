package com.zhu2chu.all.taxonomy.front;

import com.jfinal.core.Controller;
import com.zhu2chu.all.bus.router.URIMapping;
import com.zhu2chu.all.front.FrontRoutes;

/**
 * 测试ZUI框架相关功能
 * 
 * @author ThreeX
 *
 */
@URIMapping(uri="/zui",routeClass=FrontRoutes.class,viewPath="/progress")
public class ZUIController extends Controller {

    public void progress() {
        render("zuiprogress.html");
    }

}
