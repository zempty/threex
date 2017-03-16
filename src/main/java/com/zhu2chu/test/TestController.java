package com.zhu2chu.test;

import com.jfinal.config.Routes;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.zhu2chu.common.router.UrlMapping;

@UrlMapping(url="/test", viewPath="/test", routeClass={Routes.class})
public class TestController extends Controller {

    @ActionKey("/htmltest")
    public void showDocPage() {

        render("document.html");
    }

    @ActionKey("/waterfall")
    public void showWaterfall() {
        render("waterfall.html");
    }

}
