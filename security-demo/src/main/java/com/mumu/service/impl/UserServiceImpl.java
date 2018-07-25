package com.mumu.service.impl;

import com.mumu.dao.UserDao;
import com.mumu.dao.UserMapper;
import com.mumu.dto.User;
import com.mumu.dto.UserQueryCondition;
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
    private UserDao userDao;
    @Autowired
    private UserMapper userMapper;
    @Override
    public int save(User user) {
        int result = userMapper.insert(user);
        return result;
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public User find(String username) {
        return userMapper.findUserByName(username);
    }

    @Override
    public int delete(Long id) {
        return userMapper.delete(id);
    }

    @Override
    public List<User> findByCondition(UserQueryCondition condition) {
        return userMapper.findUserByCondition(condition);
    }

    @Override
    public User findById(Long id) {
        return userMapper.findUserById(id);
    }
}
