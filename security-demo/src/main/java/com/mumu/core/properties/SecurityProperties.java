package com.mumu.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 登录页面属性配置
 *    包括:图片验证码特征，登录页面特征等
 */
@ConfigurationProperties(prefix = "com.mumu")
public class SecurityProperties {
    /**
     * 配置浏览器登录类型，登录页面
     */
    private BrowserProperties browser = new BrowserProperties();
    /**
     * 配置图形验证码属性
     */
    private ValidateCodeProperties code = new ValidateCodeProperties();

    public BrowserProperties getBrowser() {
        return browser;
    }

    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }
}
