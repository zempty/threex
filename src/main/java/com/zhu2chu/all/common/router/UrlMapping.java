package com.zhu2chu.all.common.router;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jfinal.config.Routes;

/**
 * 2017年3月15日 13:07:19
 * 
 * @author ThreeX
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface UrlMapping {

    String url();
    String viewPath() default "";
    /**
     * 加入到哪个Routes里。用于路由分类，默认值为Routes.class，这是不分模块路由
     */
    Class<? extends Routes>[] routeClass() default { Routes.class };

}
