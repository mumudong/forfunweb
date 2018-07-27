package com.mumu.service.impl;

import com.mumu.dao.UserMapper;
import com.mumu.bean.UserLogin;
import com.mumu.bean.UserQueryCondition;
import com.mumu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int save(UserLogin userLogin) {
        int result = userMapper.insert(userLogin);
        return result;
    }

    @Override
    public int update(UserLogin userLogin) {
        return userMapper.update(userLogin);
    }

    @Override
    public UserLogin find(String username) {
        return userMapper.findUserByName(username);
    }

    @Override
    public int delete(Long id) {
        return userMapper.delete(id);
    }

    @Override
    public List<UserLogin> findByCondition(UserQueryCondition condition) {
        return userMapper.findUserByCondition(condition);
    }

    @Override
    public UserLogin findById(Long id) {
        return userMapper.findUserById(id);
    }
}
