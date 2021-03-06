package com.zhu2chu.all.bus.router;

import java.util.List;

import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.zhu2chu.all.bus.kit.ClassKit;

/**
 * 2017年4月14日 00:47:00
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class RouterKit {

    private static final Log log = Log.getLog(RouterKit.class);
    /**
     * 写成类变量，这样避免多次扫描
     */
    private static final List<Class<Controller>> controllerClasses = ClassKit.scanSubClass(Controller.class, true);

    /**
     * 自动扫描该路由的Controller并添加到routes里面
     */
    public static void scanController(Class<?> clzz, Routes route) {
        if (controllerClasses != null) {
            for (Class<?> cls : controllerClasses) {
                URIMapping urlMapping = cls.getAnnotation(URIMapping.class);
                if (urlMapping != null && StrKit.notBlank(urlMapping.uri())) {
                    Class<?>[] clz = urlMapping.routeClass();
                    boolean allowSub = urlMapping.allowSub();
                    if (clz.length > 0) {
                        for (int z = 0; z < clz.length; z++) {
                            if (clzz.isAssignableFrom(clz[z])) {// 如果当前路由class能指向clz[z]，说明clz[z]是当前路由的本类或子类，就添加
                                if (allowSub) {
                                    addRoute(clzz, cls, urlMapping, route);
                                } else {
                                    if (clz[z].isAssignableFrom(clzz)) {// 保证为本身类
                                        addRoute(clzz, cls, urlMapping, route);
                                    }
                                }
                            } else if (clzz.getName().contains("Config$")) {// 如果是这种情况，就是中心路由器。中心路由对象是Config的一个匿名对象。它的class对象居然不是Routes.class，真是的。
                                if (Routes.class.isAssignableFrom(clz[z]) && clz[z].isAssignableFrom(Routes.class)) {// 保证是Routes本身类
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
     * @param clzz
     *            Routes的class对象
     * @param cls
     *            Controller的class对象
     * @param urlMapping
     *            注解
     * @param route
     *            Routes的具体对象
     */
    @SuppressWarnings("unchecked")
    private static void addRoute(Class<?> clzz, Class<?> cls, URIMapping urlMapping, Routes route) {
        String clzzName = clzz.getName();
        if (PropKit.getBoolean("devMode")) {
            if (log.isDebugEnabled() && !clzzName.contains("Config$")) {// 这里不打印中心路由匿名类的添加控制器信息，统一在Config类里打印
                System.out.println(clzzName + " >>> 添加了Controller：" + cls.getCanonicalName());
            }
        }
        if (StrKit.notBlank(urlMapping.viewPath())) {
            route.add(urlMapping.uri(), (Class<? extends Controller>) cls, urlMapping.viewPath());
        } else {
            route.add(urlMapping.uri(), (Class<? extends Controller>) cls);
        }
    }

}
