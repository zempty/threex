package com.zhu2chu.all.admin.index;

import java.io.File;

import com.jfinal.kit.Kv;
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
@UrlMapping(url="/admin/index", routeClass=AdminRoutes.class, viewPath="/invalid")
public class IndexController extends BusController {

    public void index() {
        File file = new File("D:\\360Chrome\\page.html");
        String html = FileKit.onceReadToString(file);

        Kv kv = new Kv();
        kv.set("chu", "王");
        kv.set("love", "五");
        setAttr("wo", kv);

        renderHtmlWithTemplate(html);
    }

}
