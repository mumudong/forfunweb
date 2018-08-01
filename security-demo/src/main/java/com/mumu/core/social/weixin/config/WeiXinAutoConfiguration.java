package com.mumu.core.social.weixin.config;

import com.mumu.core.properties.SecurityProperties;
import com.mumu.core.properties.WeiXinProperties;
import com.mumu.core.social.weixin.WeiXinBinding;
import com.mumu.core.social.weixin.connect.WeiXinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;


/**
 * Created by Administrator on 2018/8/1.
 */
@Configuration
@ConditionalOnProperty(prefix = "com.mumu.social.weixin",name="app-id")
public class WeiXinAutoConfiguration extends SocialAutoConfigurerAdapter{
    @Autowired
    private SecurityProperties securityProperties;
    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeiXinProperties weiXinProperties = securityProperties.getSocial().getWeixin();
        return new WeiXinConnectionFactory(weiXinProperties.getProviderId(),
                                            weiXinProperties.getAppId(),
                                        weiXinProperties.getAppSecret());
    }
    @Bean({"connect/weixinConnect","connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    public View weixinConnectedView(){
        return new WeiXinBinding();
    }
}
