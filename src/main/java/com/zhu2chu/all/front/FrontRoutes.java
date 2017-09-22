package com.zhu2chu.all.front;

import com.jfinal.config.Routes;
import com.zhu2chu.all.bus.router.RouterKit;

/**
 * 页面Controller映射
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class FrontRoutes extends Routes {

    @SuppressWarnings("unchecked")
    @Override
    public void config() {
        setBaseViewPath("/induce/front");

        RouterKit.scanController(getClass(), this);
    }

}
