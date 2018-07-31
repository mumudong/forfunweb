package com.mumu.core.properties;

import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * Created by Administrator on 2018/7/31.
 */
public class QQProperties extends SocialProperties {
    private String providerId = "qq";

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
