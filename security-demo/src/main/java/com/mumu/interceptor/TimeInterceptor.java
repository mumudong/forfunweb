package com.mumu.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * 不像Filter,只声明这个注解不能是拦截器起作用
 * 可以获取哪个controller的哪个方法调用的调用的拦截，不能获取对应参数
 */
@Component
public class TimeInterceptor implements HandlerInterceptor{
    /**
     *  conroller某个方法执行前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle ...");
        System.out.println("preHandle ..." + ((HandlerMethod)handler).getBean().getClass().getName());
        System.out.println("preHandle ..." + ((HandlerMethod)handler).getMethod().getName());
        System.out.println("preHandle ..." + Arrays.asList(((HandlerMethod)handler).getMethodParameters()));
        request.setAttribute("startTime",System.currentTimeMillis());
        return true;
    }

    /**
     * controller某方法调用后调用，若出现异常,且被ExceptionController拦截，则不执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle ...");
        long start = (Long) request.getAttribute("startTime");
        System.out.println("postHandle spent time:" + (System.currentTimeMillis() - start));
    }

    /**
     * 最终都会执行
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
        System.out.println("afterCompletion ...");
        long start = (Long) request.getAttribute("startTime");
        System.out.println("afterCompletion spent time:" + (System.currentTimeMillis() - start));
        System.out.println("afterCompletion exception: " + e);
        System.out.println("afterCompletion handler: " + handler);
    }
}
