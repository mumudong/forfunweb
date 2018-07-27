package com.mumu.dao;

import com.mumu.bean.UserLogin;
import com.mumu.bean.UserQueryCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25.
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<Long,UserLogin>{
    List<UserLogin> findUserByCondition(UserQueryCondition condition);
    List<UserLogin> findUserByPage(@Param("username") String username);
    UserLogin findUserByName(@Param("username") String username);
    UserLogin findUserById(@Param("id") Long id);
}
