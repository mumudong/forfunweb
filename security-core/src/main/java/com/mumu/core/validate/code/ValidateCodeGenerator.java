package com.mumu.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码生成借口
 */
public interface ValidateCodeGenerator {
    ValidateCode generate(ServletWebRequest request);
}
