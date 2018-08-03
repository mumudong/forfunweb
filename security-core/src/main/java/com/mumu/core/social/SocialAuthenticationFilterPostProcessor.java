package com.mumu.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * Created by Administrator on 2018/8/3.
 */
public interface SocialAuthenticationFilterPostProcessor {
    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
