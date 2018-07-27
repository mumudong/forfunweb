package com.mumu.browser.config;

import com.mumu.core.properties.SecurityProperties;
import com.mumu.demo.config.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@AutoConfigureBefore(WebConfig.class)
//@ComponentScan(basePackages ={"com.mumu.acore"})
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SecurityProperties securityProperties;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("----------http login config--------");
        http.formLogin()
//		http.httpBasic()
                .loginPage("/authentication/mumu")
                .loginProcessingUrl("/authentication/yanzh")
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/mumu",securityProperties.getBrowser().getLoginPage()).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable();

    }

}
