package com.zhu2chu.all.admin.index;

import com.zhu2chu.all.admin.bus.AdminRoutes;
import com.zhu2chu.all.bus.controller.BusController;
import com.zhu2chu.all.bus.kit.FileKit;
import com.zhu2chu.all.bus.router.UrlMapping;

/**
 * 2017年6月26日 23:11:36
 * 后端首页控制者
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
@UrlMapping(url="/admin/index", routeClass=AdminRoutes.class, viewPath="/layuicms")
public class IndexController extends BusController {

    public void index() {
        StringBuilder html = FileKit.readFileByLines("D:\\soft\\danji\\page.html");

        renderHtmlWithTemplate(html.toString());
    }

}
