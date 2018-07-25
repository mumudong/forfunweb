package com.mumu.service;

import com.mumu.dto.User;
import com.mumu.dto.UserQueryCondition;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25.
 */
public interface UserService {
    public int save(User user);
    public int update(User user);
    public User find(String username);
    public int delete(Long id);
    public List<User> findByCondition(UserQueryCondition condition);
    public User findById(Long id);
}
