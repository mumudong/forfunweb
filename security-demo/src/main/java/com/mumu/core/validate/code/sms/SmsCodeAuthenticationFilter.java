package com.mumu.core.validate.code.sms;

import com.mumu.core.properties.SecurityConstants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.Assert;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private String mobileParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
    private String smsCodeParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
    private boolean postOnly = true;
    SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    public SmsCodeAuthenticationFilter(){
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,"POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(postOnly && !request.getMethod().equals("POST")){
            throw new AuthenticationServiceException("Authentication method not supported:%s" + request.getMethod());
        }
        String mobile = obtainMobile(request);
        if(mobile == null){
            mobile = "";
        }
        mobile = mobile.trim();
        ServletWebRequest webRequest = new ServletWebRequest(request,response);
        if(!mobile.equals(sessionStrategy.getAttribute(webRequest,SecurityConstants.DEFAULT_LOGIN_PHONE_NUMBER))){
            throw new AuthenticationServiceException("登陆手机号码与发送验证码手机号码不一致！");
        }
        SmsCodeAuthenticationToken authToKen = new SmsCodeAuthenticationToken(mobile);
        setDetails(request,authToKen);
        return this.getAuthenticationManager().authenticate(authToKen);
    }

    /**
     * 获取手机号
     */
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Username parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return mobileParameter;
    }

}
