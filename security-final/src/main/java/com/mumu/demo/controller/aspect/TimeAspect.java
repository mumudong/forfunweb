package com.mumu.demo.controller.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 拿不到原始的http请求和响应
 * 可以拿到调用方法参数值
 */
//@Aspect
//@Component
public class TimeAspect {
//    @Around("execution(* com.mumu.demo.controller.UserController.*(..))")
    public Object handlerControllerMethod(ProceedingJoinPoint point) throws Throwable{
        System.out.println("time aspect start ..");
        long start = System.currentTimeMillis();
        Object object = point.proceed();
//        System.out.println("result object --> " + object + " ---- " + object.getClass());
//        SourceLocation location = point.getSourceLocation();
//        System.out.println("location:" +location.getWithinType());
//        Object[] args = point.getArgs();
//        for(Object arg:args){
//            System.out.println("arg:" + arg);
//        }
        System.out.println("TimeAspect 耗时：" + (System.currentTimeMillis()-start));
        return object;
    }
}
