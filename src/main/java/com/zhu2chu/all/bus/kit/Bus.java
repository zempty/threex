package com.zhu2chu.all.bus.kit;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 2017年7月29日 10:22:00
 * 形象的公交车
 * 
 * @author ThreeX
 *
 */
public class Bus {

    /**
     * 2017年7月29日 10:45:39
     * 将request里的attributes放进一个map里便于搞事，此方法需要web环境中运行
     * 
     * @param request
     * @return
     */
    public static Map<?, ?> putRequestAttrsInMap(HttpServletRequest request) {
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
