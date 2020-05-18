package org.linn.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * 过滤器Filter是J2EE 定义的规范，它并不能获得Controller中的调用方法。
 */
@Slf4j
public class TimeFilter implements Filter {
    @Override
    public void destroy() {
        log.info("过滤器TimeFilter销毁");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("过滤器TimeFilter初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest http = (HttpServletRequest)request;

        //message.out.println(http.getRequestURL().toString().endsWith("ico"));
        if (!http.getRequestURL().toString().endsWith("ico")){
            filterChain.doFilter(request, response);
        }
    }
}
