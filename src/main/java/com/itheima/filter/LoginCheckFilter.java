package com.itheima.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.common.BaseContext;
import com.itheima.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lyz
 * @create 2024-06-14-16:01
 * 使用过滤器检查用户是否已经完成登录（使用拦截器也可以）
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")     // 该注解需要与启动类注解@ServletComponentScan一起使用
@Slf4j
public class LoginCheckFilter implements Filter {

    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestURI = request.getRequestURI();
        // 定义无需处理的请求路径：登录 + 退出 + 静态资源
        String[] urls = new String[]{"/employee/login","/employee/logout","/backend/**","/front/**"};
        // 判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        // 无需处理则直接放行
        if (check) {
            log.info("放行");
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        // 需要处理，查看登录状态，若已登录，则放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("已登录");
            // 将当前用户id存储到ThreaLocal中
            Long id = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(id);
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        // 若未登录，则向客户端响应数据
        log.info("未登录");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.getWriter().write(JSON.toJSONString(R.error("NOT Login")));
        log.info("线程id：" + Thread.currentThread().getId());
    }

    public boolean check(String[] urls, String requestURI) {
        for (String url: urls) {
            boolean match = PATH_MATCHER.match(url,requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
