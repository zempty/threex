package com.zhu2chu.all.bus.controller;

import com.jfinal.core.Controller;
import com.jfinal.render.RenderManager;
import com.zhu2chu.all.bus.kit.Bus;

/**
 * 2017年7月29日 09:32:46
 * 公交车controller，用于搞一些好玩的事情
 * 
 * 
 * @author ThreeX
 *
 */
public abstract class BusController extends Controller {

    private static final RenderManager renderManager = RenderManager.me();

    /**
     * 2017年7月29日 10:26:07
     * 从字符串形式的html中渲染模板
     * 
     * @param content
     * @return 返回渲染后的内容
     */
    public String renderToString(String content) {
        return renderManager.getEngine().getTemplateByString(content).renderToString(Bus.putRequestAttrsInMap(getRequest()));
    }

    /**
     * 2017年7月29日 10:26:57
     * 将html内容响应到客户端，此方法会进行模板的渲染<br/>如果仅仅是为了响应一个静态页面
     * 请使用 renderHtml(htmlStr)
     */
    public void renderHtmlWithTemplate(String content) {
        renderHtml(renderToString(content));
    }

}
