package wang.threex.common.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.support.http.StatViewServlet;
import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.druid.IDruidStatViewAuth;

/**
 * 替代 StatViewServlet
 */
public class DruidStatViewHandler extends Handler {

    private static final Log log = Log.getLog(DruidStatViewHandler.class);

    private IDruidStatViewAuth auth;
    private String visitPath = "/druid";
    private StatViewServlet servlet = new JFinalStatViewServlet();
    
    public DruidStatViewHandler(String visitPath) {
        this(visitPath,
            new IDruidStatViewAuth(){
                public boolean isPermitted(HttpServletRequest request) {
                    return true;
                }
            });
    }
    
    public DruidStatViewHandler(String visitPath , IDruidStatViewAuth druidStatViewAuth) {
        if (StrKit.isBlank(visitPath))
            throw new IllegalArgumentException("visitPath can not be blank");
        if (druidStatViewAuth == null)
            throw new IllegalArgumentException("druidStatViewAuth can not be null");
        
        visitPath = visitPath.trim();
        if (! visitPath.startsWith("/"))
            visitPath = "/" + visitPath;
        this.visitPath = visitPath;
        this.auth = druidStatViewAuth;
    }
    
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        //打印完整的url
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String reqUri = request.getRequestURI();
        log.debug(scheme+"://"+serverName+":"+serverPort+reqUri);
        //设置ContextPath到request里面,模板引擎要用
        request.setAttribute("CP0", contextPath);

        if (target.startsWith(visitPath)) {
            isHandled[0] = true;
            //添加JFinal.me().getContextPath()是为了适应有项目名的情况
            if (target.equals(visitPath) && !target.endsWith("/index.html")) {
                HandlerKit.redirect(JFinal.me().getContextPath() + (target += "/index.html"), request, response, isHandled);
                return;
            }
            
            try {
                servlet.service(request, response);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        else {
            next.handle(target, request, response, isHandled);
        }
    }
    
    class JFinalStatViewServlet extends StatViewServlet {
        
        private static final long serialVersionUID = 2898674199964021798L;
        
        public boolean isPermittedRequest(HttpServletRequest request) {
            return auth.isPermitted(request);
        }
        
        public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String contextPath = request.getContextPath();
            // String servletPath = request.getServletPath();
            String requestURI = request.getRequestURI();

            response.setCharacterEncoding("utf-8");

            if (contextPath == null) { // root context
                contextPath = "";
            }
            // String uri = contextPath + servletPath;
            // String path = requestURI.substring(contextPath.length() + servletPath.length());
            int index = contextPath.length() + visitPath.length();
            String uri = requestURI.substring(0, index);
            String path = requestURI.substring(index);

            if (!isPermittedRequest(request)) {
                path = "/nopermit.html";
                returnResourceFile(path, uri, response);
                return;
            }

            if ("/submitLogin".equals(path)) {
                String usernameParam = request.getParameter(PARAM_NAME_USERNAME);
                String passwordParam = request.getParameter(PARAM_NAME_PASSWORD);
                if (username.equals(usernameParam) && password.equals(passwordParam)) {
                    request.getSession().setAttribute(SESSION_USER_KEY, username);
                    response.getWriter().print("success");
                } else {
                    response.getWriter().print("error");
                }
                return;
            }

            if (isRequireAuth() //
                && !ContainsUser(request)//
                && !("/login.html".equals(path) //
                     || path.startsWith("/css")//
                     || path.startsWith("/js") //
                || path.startsWith("/img"))) {
                if (contextPath == null || contextPath.equals("") || contextPath.equals("/")) {
                    response.sendRedirect("/druid/login.html");
                } else {
                    if ("".equals(path)) {
                        response.sendRedirect("druid/login.html");
                    } else {
                        response.sendRedirect("login.html");
                    }
                }
                return;
            }

            if ("".equals(path)) {
                if (contextPath == null || contextPath.equals("") || contextPath.equals("/")) {
                    response.sendRedirect("/druid/index.html");
                } else {
                    response.sendRedirect("druid/index.html");
                }
                return;
            }

            if ("/".equals(path)) {
                response.sendRedirect("index.html");
                return;
            }

            if (path.indexOf(".json") >= 0) {
                String fullUrl = path;
                if (request.getQueryString() != null && request.getQueryString().length() > 0) {
                    fullUrl += "?" + request.getQueryString();
                }
                response.getWriter().print(process(fullUrl));
                return;
            }

            // find file in resources path
            returnResourceFile(path, uri, response);
        }
    }
}





