package com.mumu.core.properties;

/**
 * Created by Administrator on 2018/8/1.
 */
public class WeiXinProperties extends org.springframework.boot.autoconfigure.social.SocialProperties{
    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
     */
    private String providerId = "weixin";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
