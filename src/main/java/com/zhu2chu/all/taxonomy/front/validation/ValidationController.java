package com.zhu2chu.all.taxonomy.front.validation;

import com.jfinal.core.Controller;
import com.zhu2chu.all.bus.router.URIMapping;
import com.zhu2chu.all.front.FrontRoutes;

/**
 * 2017年9月25日 00:11:55
 * jquery-validation示例
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
@URIMapping(uri="/validation",routeClass=FrontRoutes.class)
public class ValidationController extends Controller {

    public void index() {
        render("index.html");
    }

}
