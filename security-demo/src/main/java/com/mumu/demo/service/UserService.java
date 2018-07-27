package com.mumu.demo.service;

import com.mumu.demo.bean.UserLogin;
import com.mumu.demo.bean.UserQueryCondition;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25.
 */
public interface UserService {
    public int save(UserLogin userLogin);
    public int update(UserLogin userLogin);
    public UserLogin find(String username);
    public int delete(Long id);
    public List<UserLogin> findByCondition(UserQueryCondition condition);
    public UserLogin findById(Long id);
}
