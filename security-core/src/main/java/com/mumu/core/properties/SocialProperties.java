package com.mumu.core.properties;

/**
 * Created by Administrator on 2018/7/31.
 */
public class SocialProperties {
    private String filterProcessesUrl = "/auth";
    private QQProperties qq = new QQProperties();
    private WeiXinProperties weixin = new WeiXinProperties();
    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public WeiXinProperties getWeixin() {
        return weixin;
    }

    public void setWeixin(WeiXinProperties weixin) {
        this.weixin = weixin;
    }
}
