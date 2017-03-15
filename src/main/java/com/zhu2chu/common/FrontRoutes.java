package com.zhu2chu.common;

import java.util.List;

import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.zhu2chu.common.router.UrlMapping;

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

        List<Class<Controller>> controllerClasses = ClassUtils.scanSubClass(Controller.class, true);
        if (controllerClasses != null) {
            for (Class<?> cls : controllerClasses) {
                UrlMapping urlMapping = cls.getAnnotation(UrlMapping.class);
                if (urlMapping != null && StrKit.notBlank(urlMapping.url())) {
                    Class<?>[] clz = urlMapping.routeClass();
                    if (clz.length > 0) {
                        Class<? extends FrontRoutes> crtClass = getClass();//获取当前类对象的class对象

                        for (int z=0; z<clz.length; z++) {
                            if (crtClass.isAssignableFrom(clz[z])) {//如果当前路由class能指向clz[z]，说明clz[z]是当前路由的本类或子类，就添加
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
        }
    }

}
