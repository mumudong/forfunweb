package com.mumu.core.config;

import com.mumu.core.dao.UserDao;
import com.mumu.core.service.MyUserDetailsService;
import com.mumu.core.service.UserService;
import com.mumu.core.validate.code.ValidateCodeRepository;
import com.mumu.core.validate.code.sms.SmsCodeAuthenticationFilter;
import com.mumu.core.validate.code.sms.SmsCodeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 短信验证码配置类，配置filter,authenticationManager,provider类
 */
@Component
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity>{
    @Autowired
    private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private ValidateCodeRepository validateCodeRepository;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        smsCodeAuthenticationFilter.setValidateCodeRepository(validateCodeRepository);

        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        MyUserDetailsService myUserDetailService = new MyUserDetailsService();
        myUserDetailService.setUserDao(userDao);
        myUserDetailService.setUserService(userService);
        smsCodeAuthenticationProvider.setUserDetailsService(myUserDetailService);

        http.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
