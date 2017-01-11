package wang.threex.core;

import java.util.ArrayList;

import com.jfinal.core.Controller;
import com.jfplugin.mail.MailKit;

public class IndexController extends Controller {
	
	public void index() {
		//MailKit.send("super@qq.com", new ArrayList<String>(), "这是一种传说中的技术", "你学会了吗？");
		render("index.html");
	}

}
