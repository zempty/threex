package com.zhu2chu.common;

import java.util.List;

import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.zhu2chu.common.router.RouterMapping;

import io.jpress.utils.ClassUtils;

/**
 * 页面Controller映射
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class FrontRoutes extends Routes {

    @SuppressWarnings("unchecked")
    @Override
    public void config() {
        setBaseViewPath("/_pages");

        List<Class<Controller>> controllerClasses = ClassUtils.scanSubClass(Controller.class);
        if (controllerClasses != null) {
            for (Class<?> cls : controllerClasses) {
                RouterMapping urlMapping = cls.getAnnotation(RouterMapping.class);
                if (urlMapping != null && StrKit.notBlank(urlMapping.url())) {
                    if (StrKit.notBlank(urlMapping.viewPath())) {
                        add(urlMapping.url(), (Class<? extends Controller>) cls, urlMapping.viewPath());
                    } else {
                        add(urlMapping.url(), (Class<? extends Controller>) cls);
                    }
                }
            }
        }
    }

}
