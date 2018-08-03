package com.mumu.app;

import com.mumu.app.social.openid.OpenIdAuthenticationSeurityConfig;
import com.mumu.core.config.SmsCodeAuthenticationSecurityConfig;
import com.mumu.core.config.ValidateCodeSecurityConfig;
import com.mumu.core.properties.SecurityConstants;
import com.mumu.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * Created by Administrator on 2018/8/2.
 */
@Configuration
@EnableResourceServer
public class AppResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    @Autowired
    private SpringSocialConfigurer mySocialSecurityConfig;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private OpenIdAuthenticationSeurityConfig openIdAuthenticationSeurityConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler);

        http
                    .apply(validateCodeSecurityConfig)
                .and()
                    .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                    .apply(mySocialSecurityConfig)
                .and()
                    .apply(openIdAuthenticationSeurityConfig)
                .and()
                    .authorizeRequests()
                     .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
                                                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
                                                SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPENID,
                                                SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
                                                securityProperties.getBrowser().getLoginPage(),
                                                securityProperties.getBrowser().getSignUpUrl(),
                                                securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".html",
                                                securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".json",
                                                securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
                                                securityProperties.getBrowser().getSignOutUrl(),
                                                "/user/regist","/social/signUp").permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .csrf().disable();
    }
}
