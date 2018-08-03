package com.mumu.app.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mumu.core.properties.LoginResponseType;
import com.mumu.core.properties.SecurityProperties;
import com.mumu.core.validate.code.ValidateCodeRepository;
import com.mumu.core.validate.code.ValidateCodeType;
import com.mumu.core.validate.code.sms.SmsCodeAuthenticationToken;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;
    @Autowired
    private ValidateCodeRepository validateCodeRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info(String.format("登录成功 %s",""));
        if(authentication instanceof SmsCodeAuthenticationToken){
            ServletWebRequest req = new ServletWebRequest(request,response);
            validateCodeRepository.remove(req, ValidateCodeType.SMS,true);
        }

        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Basic ")){
            throw new UnapprovedClientAuthenticationException("请求头中无client信息");
        }

        String[] tokens = extractAndDecodeHeader(header,request);
        String clientId = tokens[0];
        String clientSecret = tokens[1];

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if(clientDetails == null){
            throw new UnapprovedClientAuthenticationException("clientId对应的配置信息不存在:" + clientId);
        }else if(!StringUtils.equals(clientDetails.getClientSecret(),clientSecret)){
            throw new UnapprovedClientAuthenticationException("clientSecret不匹配:" + clientId);
        }

        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP,clientId,clientDetails.getScope(),"cusotm");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
        OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);

        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(token));

    }
    private String[] extractAndDecodeHeader(String header,HttpServletRequest request) throws IOException{
        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try{
            decoded = Base64.decode(base64Token);
        }catch (IllegalArgumentException e){
            throw new BadCredentialsException("Failed to decode basic authentication");
        }
        String token = new String(decoded,"UTF-8");
        logger.info("token:{}",token);
        int delimIndex = token.indexOf(":");
        if(delimIndex == -1){
            throw new BadCredentialsException("invalid basic authentication token");
        }
        return new String[] {token.substring(0,delimIndex),token.substring(delimIndex + 1)};
    }
}
