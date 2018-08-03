package com.mumu.app.social.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/3.
 */
@Component
public class OpenIdAuthenticationSeurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity>{
    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private SocialUserDetailsService userDetailsService;
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        OpenIdAuthenticationFilter openIdAuthenticationFilter = new OpenIdAuthenticationFilter();
        openIdAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        openIdAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        openIdAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));

        OpenIdAuthenticationProvider openIdAuthenticationProvider = new OpenIdAuthenticationProvider();
        openIdAuthenticationProvider.setUserDetailsService(userDetailsService);
        openIdAuthenticationProvider.setUsersConnectionRepository(usersConnectionRepository);

        builder.authenticationProvider(openIdAuthenticationProvider)
                .addFilterAfter(openIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
