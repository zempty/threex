package com.zhu2chu.all.admin.bus;

import com.jfinal.config.Routes;
import com.zhu2chu.all.admin.index.IndexController;
import com.zhu2chu.all.admin.interceptor.AdminAuthInterceptor;
import com.zhu2chu.all.bus.router.RouterKit;

/**
 * 2017年4月28日 09:50:45 后台相关controller的拦截器
 * 
 * @author ThreeX
 *
 */
public class AdminRoutes extends Routes {

    @Override
    public void config() {
        // 添加后台管理拦截器，将拦截在此方法中注册的所有 Controller
        addInterceptor(new AdminAuthInterceptor());
        //add("/admin/index", IndexController.class, "/layuicms");

        // 后台Controller的基础视图路径
        setBaseViewPath("/induce/dragonball");

        RouterKit.scanController(getClass(), this);
    }

}
