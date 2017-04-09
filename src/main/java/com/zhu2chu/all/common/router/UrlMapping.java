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
     * 加入到哪个Routes里。用于路由分类，默认值为Routes.class，这是不分模块路由。
     * 理论上来讲，这不应该是一个数组，因为同一个controllerKey只能用一次。
     * 这里先这样，保不准以后会咋样？
     */
    Class<? extends Routes>[] routeClass() default { Routes.class };
    /**
     * 是否允许添加当前Routes对象的子类Contoller。false为仅允许本身类
     * 如：FrontRoutes.class，子类：Routes2Routes extends FrontRoutes
     * 为true的话，FrontRoutes的对象也能添加注解标记为Routes2Routes.class的Controller。
     * 默认为false，标记哪个就只能添加哪个，这样省事。
     * @return
     */
    boolean allowSub() default false;

}
