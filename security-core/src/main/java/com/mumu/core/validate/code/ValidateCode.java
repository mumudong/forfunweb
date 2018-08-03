package com.mumu.core.validate.code;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 验证码基类
 */
public class ValidateCode implements Serializable{
    private static final long serialVersionUID = 9196158026302317917L;
    private String code;
    private LocalDateTime expireTime;
    private int expireSeconds;

    public ValidateCode(String code, int expireTime) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireTime);

    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
        this.expireSeconds = (int)Duration.between(LocalDateTime.now(),expireTime).toMillis()/1000;
    }

    public boolean isExpired(){
        return LocalDateTime.now().isAfter(expireTime);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }
}
