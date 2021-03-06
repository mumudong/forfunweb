package com.mumu.core.social;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component
public class DemoConnectionSignUp implements ConnectionSignUp{
    @Override
    public String execute(Connection<?> connection) {
        //根据社交信息默认创建用户,并返回用户唯一标识
        return connection.getDisplayName();
    }
}
