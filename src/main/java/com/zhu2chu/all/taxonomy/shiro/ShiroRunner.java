package com.zhu2chu.all.taxonomy.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

import com.zhu2chu.all.bus.kit.PathKit;

/**
 * 2017年8月14日 15:03:18
 * shiro框架学习
 * 
 * @author ThreeX
 *
 */
public class ShiroRunner {

    public static void main(String[] args) {
        //1.安全管理器工厂，传入生厂东西的配置文件路径
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("file:"+PathKit.getCurrentDirectory(ShiroRunner.class)+"shiro.ini");
        //2.从工厂获取一个安全管理器
        SecurityManager securityManager = factory.getInstance();
        //3.将工厂管理器扔给一个工具类，日后可以通过此工具类操纵安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //4.获取当前正和安全管理器打交道的东西
        Subject subject = SecurityUtils.getSubject();
        //5.从东西获取会话对象，可以对此会话设置属性，移除属性
        Session sess = subject.getSession();

        //如果当前打交道的东西没有经过身份验证，则先验证身份
        if (!subject.isAuthenticated()) {
            //通过用户名密码创建一个token
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            //记住这个token
            token.setRememberMe(true);
            //使用此token进行登录
            subject.login(token);
        }

        //进行配置文件里的授权检测
        if (subject.isPermitted("lightsaber:d")) {
            System.out.println("处处");
        }

        //退出系统
        subject.logout();
    }

}
