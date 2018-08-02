package com.mumu.core.validate.code;

import com.mumu.core.filter.ValidateCodeException;
import com.mumu.core.properties.SecurityConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Map;

public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor{
    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 操作session的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
    /**
     * 收集系统中所有的 {@link ValidateCodeGenerator} 接口的实现。
     */
    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerators;
    @Autowired
    private ValidateCodeRepository validateCodeRepository;

    @Override
    public void create(ServletWebRequest request) throws Exception {
        T validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }
    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private T generate(ServletWebRequest request) {
        logger.info(validateCodeGenerators.toString());
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (T) validateCodeGenerator.generate(request);
    }

    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, T validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(),validateCode.getExpireTime());
//        sessionStrategy.setAttribute(request, getSessionKey(request), code);
        validateCodeRepository.save(request,code,getValidateCodeType(request));
        ValidateCodeType type = getValidateCodeType(request);
        if(ValidateCodeType.SMS.equals(type)){
            //将需要发送短信的手机号码放入session，然后登陆时比对
            validateCodeRepository.save(request,)
            sessionStrategy.setAttribute(request, SecurityConstants.DEFAULT_LOGIN_PHONE_NUMBER,request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE));
            logger.info("登陆的手机号是：{}",request.getParameter(SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE));
        }
    }



    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, T validateCode) throws Exception;

    /**
     * 根据请求的url获取校验码的类型
     *
     * @param request
     * @return
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }
    /**
     * 构建验证码放入session时的key
     *
     * @param request
     * @return
     */
    private String getSessionKey(ServletWebRequest request) {
        return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType processorType = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);

        T codeInSession = (T) sessionStrategy.getAttribute(request, sessionKey);

        String codeInRequest;
        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
                    processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }



        if (StringUtils.isBlank(codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(processorType + "验证码不存在");
        }

        if (codeInSession.isExpired()) {
            sessionStrategy.removeAttribute(request, sessionKey);
            throw new ValidateCodeException(processorType + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
            throw new ValidateCodeException(processorType + "验证码不匹配");
        }

        sessionStrategy.removeAttribute(request, sessionKey);
    }

}
