package com.mumu.app;

import com.mumu.core.social.MySpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 这个类会在每个bean实例化的实例化的时候起作用
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        //app模式下，修改注册入口
        if(StringUtils.equals(s,"mySocialSecurityConfig")){
            MySpringSocialConfigurer config = (MySpringSocialConfigurer) o;
            config.signupUrl("/social/signUp");
            return config;
        }
        return o;
    }
}
