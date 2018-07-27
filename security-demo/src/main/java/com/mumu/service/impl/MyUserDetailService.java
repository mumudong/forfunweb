package com.mumu.service.impl;

import com.mumu.dao.UserDao;
import com.mumu.bean.UserLogin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/7/27.
 */
@Component
public class MyUserDetailService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info(String.format("登陆用户名：",username));
        UserLogin userLoggin = userDao.findByUsername(username);
        logger.info(String.format("用户信息：", userLoggin));
        String password = "";
        if(userLoggin != null)
            password = passwordEncoder.encode(userLoggin.getPassword());
        logger.info(String.format("加密密码：", password));
        return new User(username,password,true,true,true,true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
