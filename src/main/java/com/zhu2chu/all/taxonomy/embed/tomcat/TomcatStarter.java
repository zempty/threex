package com.zhu2chu.all.taxonomy.embed.tomcat;

/**
 * 2017年8月17日 09:37:52
 * embed tomcat 启动器，学习tomcat启动
 * 
 * @author ThreeX
 *
 */
public class TomcatStarter {

    public static void main(String[] args) {
        String webAppDir = "src/main/config/app";//web根目录
        /*Tomcat tomcat = new Tomcat();

        String webPort = System.getenv("PORT");
        if (webPort==null || webPort.isEmpty()) {
            webPort = "8800";
        }

        File additionWebInfClasses = new File("target/classes");
        tomcat.setBaseDir(additionWebInfClasses.getAbsolutePath());

        tomcat.setPort(Integer.parseInt(webPort));

        Context context = tomcat.addWebapp("", new File(webAppDir).getAbsolutePath());
        //热加载，不过好像没啥用
        context.setReloadable(true);

        Connector connector = tomcat.getConnector();
        //设置tomcat运行方式。不过好像只能用nio
        connector.setProtocol("org.apache.coyote.http11.Http11AprProtocol");

        System.out.println(tomcat.getHost().getCatalinaBase());
        tomcat.start();//启动tomcat
        tomcat.getServer().await();//在关机命令到来之前一直服务
*/    }

}
