package com.mumu.core.properties.browser;

import com.mumu.core.properties.LoginResponseType;
import com.mumu.core.properties.SecurityConstants;

public class BrowserProperties {
    //默认登录页
    private String loginPage = SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
    //默认登录类型
    private LoginResponseType loginType = LoginResponseType.JSON ;

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

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }
}
