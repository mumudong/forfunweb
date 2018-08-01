package com.mumu.browser.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mumu.browser.bean.SimpleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2018/8/1.
 */
public class AbstractSessionStrategy {
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 跳转URL
     */
    private String destinationUrl;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    /**
     * 跳转钱是否新建session
     */
    private boolean createNewSession = true;
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 如果访问的是html页面则将消息返回给对应页面
     * @param invalidSessionUrl
     */
    public AbstractSessionStrategy(String invalidSessionUrl){
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl));
        this.destinationUrl = invalidSessionUrl;
    }
    protected void onSessionInvalid(HttpServletRequest request,HttpServletResponse response) throws IOException{
        if(createNewSession){
            request.getSession();
        }
        String sourceUrl = request.getRequestURI();
        String targetUrl;
        if(StringUtils.endsWithIgnoreCase(sourceUrl,".html")){
            targetUrl = destinationUrl + ".html";
            logger.info("session失效，跳转到：{}",targetUrl);
            redirectStrategy.sendRedirect(request,response,targetUrl);
        }else{
            Object result = buildResponseContent(request);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(result));
        }
    }
    protected Object buildResponseContent(HttpServletRequest request) {
        String message = "session已失效";
        if(isConcurrency()){
            message = message + "，有可能是并发登录导致的";
        }
        return new SimpleResponse(message);
    }
    /**
     * session失效是否是并发导致的
     * @return
     */
    protected boolean isConcurrency() {
        return false;
    }
    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }
}
