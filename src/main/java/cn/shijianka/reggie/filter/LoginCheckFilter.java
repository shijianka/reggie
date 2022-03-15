package cn.shijianka.reggie.filter;

import cn.shijianka.reggie.common.BaseContext;
import cn.shijianka.reggie.common.R;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//过滤器注解
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter  implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //1：获取请求路径
        String requestURI = httpServletRequest.getRequestURI();
        log.info("获取到请求路径：{}",requestURI);
        //2：检查路径是否在拦截目录
            //需要排除的路径排除
        String[] arr = new String[]{
                "/",
                "/*",
            "/employee/login",
            "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",//移动端发送短信
                "/user/login"//移动端登录
        };

        //3：若是不在拦截目录放行，
        boolean check = check(arr, requestURI);
        if(check){
            log.info("{}不在拦截目录内，已经放行",requestURI);
            chain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //4-1：检查员工是否登录，已经登录则放行
            //session中查找
        if (httpServletRequest.getSession().getAttribute("employee")!=null) {
            log.info("员工已经登录，放行");
           /* long id = Thread.currentThread().getId();
            log.info("（拦截器）当前线程id:{}",id);*/
            BaseContext.set((Long)httpServletRequest.getSession().getAttribute("employee"));
            chain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //4-2：检查用户是否登录，已经登录则放行
        //session中查找
        if (httpServletRequest.getSession().getAttribute("user")!=null) {
            log.info("用户已经登录，放行");
            BaseContext.set((Long)httpServletRequest.getSession().getAttribute("user"));
            chain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //5：未登录跳转登录 通过输出流的方式向客户端写响应数据
        log.info("用户未登录，跳转登录页面...");
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }
    public boolean check(String[] urls,String url){
        for (String s : urls) {
            boolean match = PATH_MATCHER.match(s, url);
            if(match){
                return true;
            }
        }
        return false;
    }
}
