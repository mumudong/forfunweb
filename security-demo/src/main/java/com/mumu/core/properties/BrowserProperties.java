package com.mumu.core.properties;

public class BrowserProperties {
    //默认登录页
    private String loginPage = "/signin.html1";
    //默认登录类型
    private LoginType loginType = LoginType.JSON ;

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
}
