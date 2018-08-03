package com.mumu.core.validate.code;

import com.mumu.core.properties.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Created by Administrator on 2018/8/3.
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String SESSION_KEY_PREFIX = ValidateCodeProcessor.SESSION_KEY_PREFIX;
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
        sessionStrategy.setAttribute(request, getSessionKey(request,validateCodeType), code);
        if(ValidateCodeType.SMS.equals(validateCodeType)){
            //将需要发送短信的手机号码放入session，然后登陆时比对
            sessionStrategy.setAttribute(request, SecurityConstants.DEFAULT_LOGIN_PHONE_NUMBER,request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE));
            logger.info("登陆的手机号是：{}",request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE));
        }
    }

    @Override
    public ValidateCode getCode(ServletWebRequest request, ValidateCodeType validateCodeType) {
        return (ValidateCode) sessionStrategy.getAttribute(request,getSessionKey(request,validateCodeType));
    }

    @Override
    public String getPhone(ServletWebRequest request, ValidateCodeType type) {
        String phone = sessionStrategy.getAttribute(request,SecurityConstants.DEFAULT_LOGIN_PHONE_NUMBER).toString();
        logger.info("getPhone-->{}",phone);
        return phone;
    }

    @Override
    public void remove(ServletWebRequest request, ValidateCodeType validateCodeType, boolean phone) {
        sessionStrategy.removeAttribute(request,getSessionKey(request,validateCodeType));
        if(phone)
            sessionStrategy.removeAttribute(request,SecurityConstants.DEFAULT_LOGIN_PHONE_NUMBER);
    }

    private String getSessionKey(ServletWebRequest request,ValidateCodeType validateCodeType){
        return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
    }

}
