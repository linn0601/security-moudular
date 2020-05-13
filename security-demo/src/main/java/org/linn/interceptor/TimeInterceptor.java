package org.linn.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 拦截器一般用于拦截请求，他没有办法获取到请求的参数的值
 */
@Slf4j
public class TimeInterceptor implements HandlerInterceptor {
    /**
     * 在方法调用之前被处理
     * handler 值被处理的方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", new Date().getTime());
        //获得类名
        System.out.println(((HandlerMethod) handler).getBean().getClass().getName());
        //获得方法名
        System.out.println(((HandlerMethod) handler).getMethod().getName());
        return true;
    }

    /**
     * 在方法调用之后被处理
     * handler 值被处理的方法
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (long) request.getAttribute("startTime");
        System.out.println("耗时：" + (new Date().getTime() - startTime)) ;
    }

    //不过是否被抛出异常
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("interceptor 处理完成");
    }
}
