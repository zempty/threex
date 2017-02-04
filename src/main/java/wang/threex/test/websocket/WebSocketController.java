package wang.threex.test.websocket;

import com.jfinal.core.Controller;

public class WebSocketController extends Controller {

    public void index() {
        render("ws.html");
    }

}
