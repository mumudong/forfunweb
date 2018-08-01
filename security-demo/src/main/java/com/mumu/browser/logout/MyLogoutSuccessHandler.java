package com.mumu.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mumu.browser.bean.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyLogoutSuccessHandler implements LogoutSuccessHandler{
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ObjectMapper objectMapper = new ObjectMapper();
    private String signOutSuccessUrl;
    public MyLogoutSuccessHandler(String signOutSuccessUrl) {
        this.signOutSuccessUrl = signOutSuccessUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("退出成功");
        if(StringUtils.isBlank(signOutSuccessUrl)){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        }else{
            response.sendRedirect(signOutSuccessUrl);
        }
    }
}
