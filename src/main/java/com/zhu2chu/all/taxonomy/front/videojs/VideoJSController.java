package com.zhu2chu.all.taxonomy.front.videojs;

import com.jfinal.core.Controller;
import com.zhu2chu.all.bus.router.URIMapping;
import com.zhu2chu.all.front.FrontRoutes;

/**
 * 2017年10月2日 10:27:44
 * VideoJS学习
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
@URIMapping(uri="/videojs",routeClass=FrontRoutes.class)
public class VideoJSController extends Controller {

    public void index() {
        render("index.html");
    }

}
