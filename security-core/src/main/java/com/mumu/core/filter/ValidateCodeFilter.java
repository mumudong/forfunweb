package com.mumu.core.filter;

import com.mumu.core.properties.SecurityConstants;
import com.mumu.core.properties.SecurityProperties;
import com.mumu.core.validate.code.ValidateCodeProcessorHolder;
import com.mumu.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 验证码过滤器，校验验证码是否正确
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{
    @Autowired
    private AuthenticationFailureHandler myAuthenticationFailureHandler;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    private Map<String,ValidateCodeType> urlMap = new HashMap<>();
    @Autowired
    private SecurityProperties securityProperties;
    private AntPathMatcher pathMatcher = new AntPathMatcher();
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 按类型将要过滤的Url放入map，Key:url,value:type
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM,ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(),ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE,ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(),ValidateCodeType.SMS);
    }
    /**
     * 将需要验证码的URL根据校验类型放入Map中
     */
    protected void addUrlToMap(String urlString,ValidateCodeType type){
        if(StringUtils.isNotBlank(urlString)){
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString,",");
            for(String url:urls){
                urlMap.put(url,type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ValidateCodeType type = getValidateCodeType(request);
        if(type != null){
            logger.info(String.format("校验请求(%s)中的验证码，验证码类型：%s",request.getRequestURI(),type));
            try{
                validateCodeProcessorHolder.findValidateCodeProcessor(type)
                                        .validate(new ServletWebRequest(request,response));
                logger.info("验证码校验通过");
            }catch(ValidateCodeException e){
                myAuthenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }
        /**
         * 这个地方写到上面的括号里面了，找了半天才找出来。。。。
         */
        filterChain.doFilter(request,response);
    }
    /**
     * 获取校验码类型
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request){
        ValidateCodeType result = null;
        if(!StringUtils.equalsIgnoreCase(request.getMethod(),"get")){
            Set<String> urls = urlMap.keySet();
            for(String url:urls){
                if(pathMatcher.match(url,request.getRequestURI())){
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }
}
