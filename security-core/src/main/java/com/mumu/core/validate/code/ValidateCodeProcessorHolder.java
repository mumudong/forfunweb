package com.mumu.core.validate.code;

import com.mumu.core.filter.ValidateCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 根据类型找匹配的Processor
 */
@Component
public class ValidateCodeProcessorHolder {
    @Autowired
    private Map<String,ValidateCodeProcessor> validateCodeProcessors;
    public ValidateCodeProcessor findValidateCodeProcessor(ValidateCodeType codeType){
        String type = codeType.toString().toLowerCase();
        return findValidateCodeProcessor(type);
    }
    public ValidateCodeProcessor findValidateCodeProcessor(String type){
        String key = type + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor processor = validateCodeProcessors.get(key);
        if(processor == null){
            throw new ValidateCodeException(String.format("验证码处理器：%s不存在",key));
        }
        return processor;
    }
}
