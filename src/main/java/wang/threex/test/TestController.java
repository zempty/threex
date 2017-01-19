package wang.threex.test;

import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;

public class TestController extends Controller {

    @ActionKey("/htmltest")
    public void showDocPage() {
        render("document.html");
    }

    @ActionKey("/waterfall")
    public void showWaterfall() {
        render("waterfall.html");
    }

}
