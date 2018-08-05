package com.mumu.app;

import com.mumu.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class TokenStoreConfig {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Bean
    @ConditionalOnProperty(prefix = "com.mumu.oauth2",name = "tokenStore",havingValue = "redis")
    public TokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Configuration
    @ConditionalOnProperty(prefix = "com.mumu.oauth2",name = "tokenStore",havingValue = "jwt",matchIfMissing = true)
    public static class JwtConfig{
        @Autowired
        private SecurityProperties securityProperties;
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            //设置数字签名，jwt没有加密，但有签名不会篡改信息，若签名泄露，信息可能被篡改
            converter.setSigningKey(securityProperties.getOauth2().getJwtSigningKey());
            return converter;
        }
        @Bean
        public TokenStore jwtTokenStore(){
            return new JwtTokenStore(jwtAccessTokenConverter());
        }
        @Bean
        @ConditionalOnBean(TokenEnhancer.class)
        public TokenEnhancer jwtTokenEnhancer(){
            return new MyJwtTokenEnhancer();
        }
    }
}
