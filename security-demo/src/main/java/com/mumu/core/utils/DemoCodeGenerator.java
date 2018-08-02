package com.mumu.core.utils;

import com.mumu.core.validate.code.image.ImageCode;
import com.mumu.core.validate.code.ValidateCodeGenerator;
import org.springframework.web.context.request.ServletWebRequest;
//@Component("imageCodeGenerator")
public class DemoCodeGenerator implements ValidateCodeGenerator {
    @Override
    public ImageCode generate(ServletWebRequest request) {
        System.out.println("自定义验证码生成器");
        return null;
    }
}
