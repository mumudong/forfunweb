package com.mumu.core.validate.code;

import com.mumu.core.properties.SecurityConstants;

public enum ValidateCodeType {
    /**
     * 短信验证码类型
     */
    SMS{
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     * 图片验证码类型
     */
    IMAGE{
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };
    public abstract String getParamNameOnValidate();
}
