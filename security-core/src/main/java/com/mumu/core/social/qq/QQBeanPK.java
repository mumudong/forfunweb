package com.mumu.core.social.qq;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/1.
 */
public class QQBeanPK implements Serializable {

    private String userId ;
    private String providerId;
    private String providerUserId;

    public QQBeanPK() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        QQBeanPK other = (QQBeanPK)obj;
        if(this.getUserId().equals(other.getUserId())
                && this.getProviderId().equals(other.getProviderId() )
                && this.getProviderUserId().equals(other.getProviderUserId()))
            return true;
        else
            return false;
    }
}
