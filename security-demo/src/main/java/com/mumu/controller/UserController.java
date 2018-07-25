package com.mumu.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mumu.dto.User;
import com.mumu.dto.UserQueryCondition;
import com.mumu.service.UserService;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/25.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public Map create(@Valid @RequestBody User user /* BindingResult errors*/){
//        使用json字符串传递参数
//        if(errors.hasErrors())
//            errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        System.out.println(user);
        int result = userService.save(user);
        Map<String,Object> map  = new HashMap<>();
        map.put("user",user);
        map.put("result",result);
        return map;
    }

    @PutMapping("/{id:\\d+}")
    public Map update(@Valid @RequestBody User user,BindingResult errors){
        if(errors.hasErrors())
            errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        System.out.println(user);
        int result = userService.update(user);
        Map<String,Object> map  = new HashMap<>();
        map.put("user",user);
        map.put("result",result);

        return map;
    }

    @DeleteMapping("/{id:\\d+}")
    public int delete(@PathVariable Long id){
        return userService.delete(id);
    }

    @GetMapping
    @JsonView(User.UserSimpleView.class)
    public List<User> query(UserQueryCondition condition){
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
        return userService.findByCondition(condition);
    }
    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getInfo(@PathVariable Long id){
        User user = userService.findById(id);
        System.out.println(user);
        return user;
    }
}
