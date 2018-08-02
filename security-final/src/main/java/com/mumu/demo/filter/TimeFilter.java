package com.mumu.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * 只能拿到请求和响应
 * 不知道由哪个controller控制器哪个方法调用的
 */
//@Component
public class TimeFilter implements Filter{
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("time filter init ...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("time filter dofilter");
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,servletResponse);
        long end = System.currentTimeMillis();
        logger.info(String.format("do filter time spent:%d",end-start));
    }

    @Override
    public void destroy() {
//        System.out.println("time filter destroy");
    }
}
