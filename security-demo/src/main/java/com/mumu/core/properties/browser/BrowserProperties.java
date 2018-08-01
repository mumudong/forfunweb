package com.mumu.core.properties.browser;

import com.mumu.core.properties.LoginResponseType;
import com.mumu.core.properties.SecurityConstants;
import com.mumu.core.properties.SessionProperties;

public class BrowserProperties {
    private SessionProperties session = new SessionProperties();
    //默认登录页
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
    //默认登录类型
    private LoginResponseType loginType = LoginResponseType.JSON ;

    private String signUpUrl = "/demo-signup.html";

    private int rememberMeSeconds = 60 * 60 * 24 * 7;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginResponseType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginResponseType loginType) {
        this.loginType = loginType;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public SessionProperties getSession() {
        return session;
    }

    public void setSession(SessionProperties session) {
        this.session = session;
    }
}
