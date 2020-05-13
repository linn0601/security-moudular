package org.linn.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 切片
 * 切点，指定切片作用的地方
 */
@Component
@Aspect
@EnableAspectJAutoProxy
public class TimeAspect {

    @Around("execution(* org.linn.controller.UserController.*(..))")
    public Object handler(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("time aspect start ");
        long start = System.currentTimeMillis();
        //获取方法参数
        Object[] args = pjp.getArgs();

        for (Object arg : args){
            System.out.println(arg);
        }
        Object proceed = pjp.proceed();
        long end = System.currentTimeMillis();
        System.out.println("time aspect end :" + (end - start));

        return proceed;
    }

}
