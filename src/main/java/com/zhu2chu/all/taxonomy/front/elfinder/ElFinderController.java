package com.zhu2chu.all.taxonomy.front.elfinder;

import com.zhu2chu.all.bus.router.URIMapping;
import com.zhu2chu.all.front.FrontRoutes;
import com.zhu2chu.elfinder.connector.ConnectorController;

/**
 * 继承ConnectorController，然后index()返回视图即可玩耍
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
@URIMapping(uri="elfinder",routeClass=FrontRoutes.class)
public class ElFinderController extends ConnectorController {

    public void index() {
        render("index.html");
    }

}
