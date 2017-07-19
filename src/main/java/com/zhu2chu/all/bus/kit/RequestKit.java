package com.zhu2chu.all.bus.kit;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

/**
 * 2017年7月19日 12:39:15
 * request帮助类
 * 
 * @author ThreeX
 *
 */
public class RequestKit {

    /**
     * 2017年7月19日 12:39:49
     * 
     * 在读取request流拿到数据后，数据是json字符串，对其进行解析然后设置进request的属性，供后续
     * 流程使用。
     *
     * @param jsonStr
     */
    public static void putParaInRequest(HttpServletRequest request, String jsonStr) {
        JSONObject jsonO = JSONObject.parseObject(jsonStr);
        for (Map.Entry<String, Object> en : jsonO.entrySet()) {
            request.setAttribute(en.getKey(), en.getValue());
        }
    }

}
