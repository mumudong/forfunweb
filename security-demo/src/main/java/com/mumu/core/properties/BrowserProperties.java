package com.mumu.core.properties;

public class BrowserProperties {
    //默认登录页
    private String loginPage = "/signin.html1";
    //默认登录类型
    private LoginType loginType = LoginType.JSON ;

    private int rememberMeSeconds = 60 * 60 * 24 * 7;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }
}
