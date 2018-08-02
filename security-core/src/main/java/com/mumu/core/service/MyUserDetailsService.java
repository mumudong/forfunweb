package com.mumu.core.service;

import com.mumu.core.bean.UserLogin;
import com.mumu.core.dao.QQBeanDao;
import com.mumu.core.dao.UserDao;
import com.mumu.core.social.qq.QQBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * 登录验证UserServiceDetail
 */
@Component("userDetailsService")
public class MyUserDetailsService implements UserDetailsService,SocialUserDetailsService{
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private QQBeanDao qqBeanDao;
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
        UserLogin user = userService.findByPhone(phone);
        logger.info(String.format("用户信息：%s",user));
        // 根据用户名查找用户信息
        //根据查找到的用户信息判断用户是否被冻结

        return new User(user.getUsername(), "六六六",
                true, true, true, true,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        //可以是名字，id等，只要唯一即可
        logger.info("登录用户ID:{}",userId);
        QQBean qqBean = qqBeanDao.findByUserId(userId);
        return new SocialUser(userId,qqBean.getSecret(),true,true,
                true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
