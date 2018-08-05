package com.mumu.browser.config;

import com.mumu.core.config.*;
import com.mumu.core.properties.SecurityConstants;
import com.mumu.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

@Configuration
//@AutoConfigureBefore(WebConfig.class)
//@ComponentScan(basePackages ={"com.mumu.acore"})
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig {
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
    @Autowired
    private SpringSocialConfigurer mySocialSecurityConfig;
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private AuthorizeConfigManager myAuthorizeConfigManager;
    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        tokenRepository.setCreateTableOnStartup(false);
        return tokenRepository;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        applyPasswordAuthenticationConfig(http);

        http
                   .apply(validateCodeSecurityConfig)
                .and()
                    .apply(smsCodeAuthenticationSecurityConfig)
                .and()
                    .apply(mySocialSecurityConfig)
                .and()
                    .rememberMe()
                    .tokenRepository(persistentTokenRepository())
                    .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                    .userDetailsService(userDetailsService)
                .and()
                    .sessionManagement()
                    .invalidSessionStrategy(invalidSessionStrategy)
                    .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                    .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
                    .expiredSessionStrategy(sessionInformationExpiredStrategy)
                    .and()
                .and()
                    .logout()
                    .logoutUrl("/signOut")
                    .logoutSuccessHandler(logoutSuccessHandler)
                    .deleteCookies("JSESSIONID")
//                .and()
//                    .authorizeRequests()
//                    .antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
//                            SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,
//                            securityProperties.getBrowser().getLoginPage(),
//                            SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
//                            securityProperties.getBrowser().getSignUpUrl(),
//                            securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".html",
//                            securityProperties.getBrowser().getSession().getSessionInvalidUrl() + ".json",
//                            securityProperties.getBrowser().getSession().getSessionInvalidUrl(),
//                            securityProperties.getBrowser().getSignOutUrl(),
//                            "/user/regist").permitAll()
//                    .anyRequest()
//                    .authenticated()
                .and()
                  .csrf().disable();

        myAuthorizeConfigManager.config(http.authorizeRequests());
    }

}
