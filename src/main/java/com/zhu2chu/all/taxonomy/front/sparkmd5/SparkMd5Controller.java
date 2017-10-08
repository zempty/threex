package com.zhu2chu.all.taxonomy.front.sparkmd5;

import com.jfinal.core.Controller;
import com.zhu2chu.all.bus.router.URIMapping;
import com.zhu2chu.all.front.FrontRoutes;

/**
 * 2017年10月8日 21:00:35
 * SparkMd5学习，主要用于计算文件的md5
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
@URIMapping(uri="/sparkmd5",routeClass=FrontRoutes.class)
public class SparkMd5Controller extends Controller {

    public void index() {
        render("index.html");
    }

}
