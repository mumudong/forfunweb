package com.mumu.app.code;

import com.mumu.core.filter.ValidateCodeException;
import com.mumu.core.properties.SecurityConstants;
import com.mumu.core.validate.code.ValidateCode;
import com.mumu.core.validate.code.ValidateCodeRepository;
import com.mumu.core.validate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.concurrent.TimeUnit;

/**
 * Token store 在哪儿？
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type){
        redisTemplate.opsForValue().set(buildKey(request,type,false),code,code.getExpireSeconds(), TimeUnit.SECONDS);
        if(ValidateCodeType.SMS.equals(type)){
            //如果是短信验证码需要将手机号存储比对
            String mobile = request.getParameter("mobile");
            redisTemplate.opsForValue().set(buildKey(request,type,true),mobile,code.getExpireSeconds(), TimeUnit.SECONDS);
        }
    }

    @Override
    public ValidateCode getCode(ServletWebRequest request,ValidateCodeType type){
        Object value = redisTemplate.opsForValue().get(buildKey(request,type,false));
        if(value == null){
            return null;
        }
        return (ValidateCode) value;
    }
    @Override
    public String getPhone(ServletWebRequest request,ValidateCodeType type){
        Object value = redisTemplate.opsForValue().get(buildKey(request,type,true));
        if(value == null){
            return null;
        }
        return (String) value;
    }

    @Override
    public void remove(ServletWebRequest request,ValidateCodeType type,boolean phone){
        redisTemplate.delete(buildKey(request,type,false));
        if(phone){
            redisTemplate.delete(buildKey(request,type,true));
        }
    }

    private String buildKey(ServletWebRequest request,ValidateCodeType type,boolean phone){
        String deviceId = request.getHeader("deviceId");
        if(StringUtils.isBlank(deviceId)){
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        if(phone == false)
            return "code:" + type.toString().toLowerCase() + ":" + deviceId;
        else
            return SecurityConstants.DEFAULT_LOGIN_PHONE_NUMBER + ":" + type.toString().toLowerCase() + ":" + deviceId;
    }
}
