package com.mumu.core.validate.code.sms;

import org.springframework.stereotype.Component;

@Component("smsCodeSender")
public class DefaultSmsCodeSender implements SmsCodeSender {
    @Override
    public void send(String mobile, String code) {
        System.out.println(String.format("向手机%s发送验证码:%s",mobile,code));
    }
}
