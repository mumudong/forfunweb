package com.mumu.core.validate.code.sms;

import com.mumu.core.properties.SecurityConstants;
import com.mumu.core.validate.code.AbstractValidateCodeProcessor;
import com.mumu.core.validate.code.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {
    /**
     * 验证码发送服务
     */
    @Autowired
    private SmsCodeSender smsCodeSender;
    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
            String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
            String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(),paramName);
            smsCodeSender.send(mobile,validateCode.getCode());
    }
}
