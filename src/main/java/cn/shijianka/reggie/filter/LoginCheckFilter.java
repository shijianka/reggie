package cn.shijianka.reggie.filter;

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
        //2：检查路径是否在拦截目录
            //需要排除的路径排除
        String[] arr = new String[]{
            "/employee/login",
            "/employee/logout",
                "/backend/**",
                "/front/**"
        };

        //3：若是不在拦截目录放行，
        boolean check = check(arr, requestURI);
        if(check){
            chain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //4：检查是否登录，已经登录则放行
            //session中查找
        if (httpServletRequest.getSession().getAttribute("employee")!=null) {
            chain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //5：未登录跳转登录 通过输出流的方式向客户端写响应数据
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
