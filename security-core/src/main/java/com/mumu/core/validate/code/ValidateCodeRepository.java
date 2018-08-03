package com.mumu.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码工具类
 */
public interface ValidateCodeRepository {
    void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType);
    ValidateCode getCode(ServletWebRequest request,ValidateCodeType validateCodeType);
    String getPhone(ServletWebRequest request,ValidateCodeType type);
    void remove(ServletWebRequest request,ValidateCodeType validateCodeType,boolean phone);
}
