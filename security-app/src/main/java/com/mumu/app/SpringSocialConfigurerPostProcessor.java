package com.mumu.app;

import com.mumu.core.social.MySpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if(StringUtils.equals(s,"mySocialSecurityConfig")){
            MySpringSocialConfigurer config = (MySpringSocialConfigurer) o;
            config.signupUrl("/social/signUp");
            return config;
        }
        return o;
    }
}
