package com.mumu.browser.controller;

import com.mumu.browser.bean.SimpleResponse;
import com.mumu.core.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class BrowserSecurityController {
    private RequestCache requestCache = new HttpSessionRequestCache();
    private Logger logger = LoggerFactory.getLogger(getClass());
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Autowired
    private SecurityProperties securityProperties;
    /**
     * 访问页面时若验证未通过，则引导登录跳转
     * @param request
     * @return
     */
    @RequestMapping("/authentication/mumu")
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response)
                                throws Exception{
        SavedRequest savedRequest = requestCache.getRequest(request,response);
        if(savedRequest != null){
            String fromUrl = savedRequest.getRedirectUrl();
            logger.info(String.format("引发跳转的请求是：%s",fromUrl));
            if(StringUtils.endsWithIgnoreCase(fromUrl,".html")){
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse("访问的服务需要身份认证，请引导用户登录");
    }
}
