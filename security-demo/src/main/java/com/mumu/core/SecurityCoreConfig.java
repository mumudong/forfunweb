package com.mumu.core;

import com.mumu.browser.config.BrowserSecurityConfig;
import com.mumu.core.properties.SecurityProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(BrowserSecurityConfig.class)
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {
}
