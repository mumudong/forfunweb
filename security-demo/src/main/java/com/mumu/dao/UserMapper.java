package com.mumu.dao;

import com.mumu.dto.User;
import com.mumu.dto.UserQueryCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/7/25.
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<Long,User>{
    List<User> findUserByCondition(@Param("condition") UserQueryCondition condition);
    List<User> findUserByPage(@Param("username") String username);
    User findUserByName(@Param("username") String username);
    User findUserById(@Param("id") Long id);
}
