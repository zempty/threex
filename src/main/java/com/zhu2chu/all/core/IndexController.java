package com.zhu2chu.all.core;

import com.jfinal.core.Controller;
import com.zhu2chu.all.bus.router.URIMapping;
import com.zhu2chu.all.front.FrontRoutes;

@URIMapping(uri="/", routeClass={FrontRoutes.class})
public class IndexController extends Controller {

    public void index() {
        // MailKit.send("super@qq.com", new ArrayList<String>(), "这是一种传说中的技术",
        // "你学会了吗？");
        render("index.html");
        //Db.update("DROP TABLE IF EXISTS TEST;CREATE TABLE TEST(ID INT PRIMARY KEY,NAME VARCHAR(255));INSERT INTO TEST VALUES(1, 'Hello');INSERT INTO TEST VALUES(2, 'World');");
        //List<Record> list = Db.find("SELECT * FROM TEST ORDER BY ID");

        //System.out.println(list);
    }

}
