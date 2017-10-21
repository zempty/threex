package com.zhu2chu.all.taxonomy.front.easyui;

import com.jfinal.core.Controller;
import com.zhu2chu.all.bus.router.URIMapping;
import com.zhu2chu.all.front.FrontRoutes;

@URIMapping(uri="/easyui",routeClass=FrontRoutes.class)
public class EasyUIController extends Controller {

    public void index() {
        render("combotreegrid/basic.html");
    }

    public void from() {
        render("from/basic.html");
    }

}
