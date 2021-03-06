package com.mumu.core.properties;

import com.mumu.core.properties.browser.BrowserProperties;
import com.mumu.core.properties.code.ValidateCodeProperties;
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
    /**
     * 社交登录配置
     */
    private SocialProperties social = new SocialProperties();

    private OAuth2Properties oauth2 = new OAuth2Properties();

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

    public SocialProperties getSocial() {
        return social;
    }

    public void setSocial(SocialProperties social) {
        this.social = social;
    }

    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    public void setOauth2(OAuth2Properties oauth2) {
        this.oauth2 = oauth2;
    }
}
