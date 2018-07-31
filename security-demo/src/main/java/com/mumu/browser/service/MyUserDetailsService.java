package com.mumu.browser.service;

import com.mumu.browser.dao.UserDao;
import com.mumu.demo.bean.UserLogin;
import com.mumu.demo.service.UserService;
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

/**
 * 登录验证UserServiceDetail
 */
@Component
public class MyUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("登录用户名:" + username);
        UserLogin user = userDao.findByUsername(username);
        logger.info(String.format("用户信息：%s",user));
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结
        String password = passwordEncoder.encode(user.getPassword());
        logger.info("数据库密码是:" + password);
        return new User(username, password,
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        logger.info("登录号码:{}",phone);
        logger.info("dao:{}",userDao);
        UserLogin user = userService.findByPhone(phone);
        logger.info(String.format("用户信息：%s",user));
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结

        return new User(user.getUsername(), "",
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
