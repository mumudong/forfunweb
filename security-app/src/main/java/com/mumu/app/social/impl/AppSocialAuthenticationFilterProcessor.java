package com.mumu.app.social.impl;

import com.mumu.core.social.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/3.
 */
@Component
public class AppSocialAuthenticationFilterProcessor implements SocialAuthenticationFilterPostProcessor {
    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        socialAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
    }
}
