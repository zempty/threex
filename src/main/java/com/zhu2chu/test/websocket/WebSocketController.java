package com.zhu2chu.test.websocket;

import com.jfinal.core.Controller;

public class WebSocketController extends Controller {

    public void index() {
        render("ws.html");
    }

}
