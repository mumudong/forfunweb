package com.mumu.app.code;

import java.util.concurrent.TimeUnit;

@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository{
    @AutoWired
    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public void save(ServletWebRequest request,ValidateCode code,ValidateCodeType type){
        redisTemplate.opsForValue().set(buildKey(request,type),code,1, TimeUnit.MINUTES);
    }

    @Override
    public ValidateCode get(ServletWebRequest request,ValidateCodeType type){
        Object value = redisTemplate.opsForValue().get(buildKey(request,type));
        if(value == null){
            return null;
        }
        return (ValidateCode) value;
    }

    @Override
    public void remove(ServletWebRequest request,ValidateCodeType type){
        redisTemplate.delete(buildKey(request,type));
    }

    private String buildKey(ServletWebRequest request,ValidateCodeType type){
        String deviceId = request.getHeader("deviceId");
        if(StringUtils.isBlank(deviceId)){
            throw new ValidateCodeException("请在请求头中携带deviceId参数");
        }
        return "code:" + type.toString().toLowerCase() + ":" + deviceId;
    }
}
