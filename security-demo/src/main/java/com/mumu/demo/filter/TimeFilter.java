package com.mumu.demo.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 只能拿到请求和响应
 * 不知道由哪个controller控制器哪个方法调用的
 */
//@Component
public class TimeFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("time filter init ...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("time filter dofilter");
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,servletResponse);
        long end = System.currentTimeMillis();
        System.out.println(String.format("do filter time spent:%d",end-start));
    }

    @Override
    public void destroy() {
//        System.out.println("time filter destroy");
    }
}
