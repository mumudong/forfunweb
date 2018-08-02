package com.mumu.core.validate.code;

/**
 * 验证码工具类
 */
public interface ValidateCodeRepository {
    void save(ServletWebRequest request,ValidateCode code,ValidateCodeType validateCodeType);
    ValidateCode get(ServletWebRequest request,ValidateCodeType validateCodeType);
    void remove(ServletWebRequest request,ValidateCodeType validateCodeType);
}
