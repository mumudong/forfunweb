package com.mumu.core.validate.code.sms;

import com.mumu.browser.service.MyUserDetailService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider{

//    private UserDetailsService userDetailsService;
    private MyUserDetailService userDetailsService;
    /**
     * 手机号码短信登陆不存在类似用户名密码验证的问题，只需校验短信即可。
     * 此处获取用户信息，能获取说明是老用户，不能获取说明是新用户。
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;
        UserDetails user = userDetailsService.loadUserByPhone((String)authenticationToken.getPrincipal());
        if(user == null){
            throw new InternalAuthenticationServiceException("无法获取用户信息！");
        }
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(user.getAuthorities(),user);
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(MyUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
