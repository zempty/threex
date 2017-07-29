package com.zhu2chu.all.bus.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.core.Controller;
import com.jfinal.render.RenderManager;

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
        return renderManager.getEngine().getTemplateByString(content).renderToString(putRequestAttrsInMap(getRequest()));
    }

    /**
     * 2017年7月29日 10:26:57
     * 将html内容响应到客户端，此方法会进行模板的渲染<br/>如果仅仅是为了响应一个静态页面
     * 请使用 renderHtml(htmlStr)
     */
    public void renderHtmlWithTemplate(String content) {
        renderHtml(renderToString(content));
    }

    /**
     * 2017年7月29日 19:16:06
     * 将request里的attributes放进一个map里便于搞事，此方法需要web环境中运行
     * 此方法在Bus.putRequestAttrsInMap也有一份，单独写入这里是因为不想此BusController因此小方法依赖工具类
     * 而工具类也保留，其它地方可能也用到，先这样先
     * 
     * @param request
     * @return
     */
    protected Map<?, ?> putRequestAttrsInMap(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> names = request.getAttributeNames();

        while (names.hasMoreElements()) {
            String name = names.nextElement();
            Object value = request.getAttribute(name);

            map.put(name, value);
        }

        return map;
    }

}
