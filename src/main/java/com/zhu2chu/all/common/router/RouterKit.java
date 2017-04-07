package com.zhu2chu.all.common.router;

import java.util.List;

import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;

import io.jpress.utils.ClassUtils;

public class RouterKit {

    private static final Log log = Log.getLog(RouterKit.class);

    /**
     * 自动扫描该路由的Controller并添加到routes里面
     */
    public static void scanController(Class<?> clzz, Routes route) {
        List<Class<Controller>> controllerClasses = ClassUtils.scanSubClass(Controller.class, true);
        if (controllerClasses != null) {
            for (Class<?> cls : controllerClasses) {
                UrlMapping urlMapping = cls.getAnnotation(UrlMapping.class);
                if (urlMapping != null && StrKit.notBlank(urlMapping.url())) {
                    Class<?>[] clz = urlMapping.routeClass();
                    if (clz.length > 0) {
                        for (int z=0; z<clz.length; z++) {
                            if (clzz.isAssignableFrom(clz[z])) {//如果当前路由class能指向clz[z]，说明clz[z]是当前路由的本类或子类，就添加
                                addRoute(clzz, cls, urlMapping, route);
                            } else if (clzz.getName().contains("Config$")) {//如果是这种情况，就是中心路由器
                                if (Routes.class.isAssignableFrom(clz[z]) && clz[z].isAssignableFrom(Routes.class)) {//保证是Routes本身类
                                    addRoute(clzz, cls, urlMapping, route);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 方法提取出来，为了减少代码冗余
     * 
     * @param clzz Routes的class对象
     * @param cls Controller的class对象
     * @param urlMapping 注解
     * @param route Routes的具体对象
     */
    private static void addRoute(Class<?> clzz, Class<?> cls, UrlMapping urlMapping, Routes route) {
        String clzzName = clzz.getName();
        if (log.isDebugEnabled() && !clzzName.contains("Config$")) {//这里不打印中心路由匿名类的添加控制器信息，统一在Config类里打印
            System.out.println(clzzName + " >>> 添加了Controller：" + cls.getCanonicalName());
        }
        if (StrKit.notBlank(urlMapping.viewPath())) {
            route.add(urlMapping.url(), (Class<? extends Controller>) cls, urlMapping.viewPath());
        } else {
            route.add(urlMapping.url(), (Class<? extends Controller>) cls);
        }
    }

}
