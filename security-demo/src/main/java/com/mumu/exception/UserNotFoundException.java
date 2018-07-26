package com.mumu.exception;

public class UserNotFoundException extends RuntimeException {
    private long id;

    public UserNotFoundException(long id) {
        super(String.format("用户id:%d,该用户不存在!",id));
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
