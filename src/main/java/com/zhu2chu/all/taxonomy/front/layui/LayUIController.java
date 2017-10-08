package com.zhu2chu.all.taxonomy.front.layui;

import com.jfinal.core.Controller;
import com.zhu2chu.all.bus.router.URIMapping;
import com.zhu2chu.all.front.FrontRoutes;

/**
 * layui研究
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
@URIMapping(uri="/layui",routeClass=FrontRoutes.class)
public class LayUIController extends Controller {

    public void index() {
        render("index.html");
    }

}
