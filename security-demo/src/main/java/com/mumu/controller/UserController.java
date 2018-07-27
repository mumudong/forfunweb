package com.mumu.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mumu.bean.UserLogin;
import com.mumu.bean.UserQueryCondition;
import com.mumu.exception.UserNotFoundException;
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
    public Map create(@Valid @RequestBody UserLogin userLogin /* BindingResult errors*/){
//        使用json字符串传递参数
/**        if(errors.hasErrors())
            errors.getAllErrors().stream().forEach(resources.error -> System.out.println(resources.error.getDefaultMessage()));*/
        System.out.println(userLogin);
        int result = userService.save(userLogin);
        Map<String,Object> map  = new HashMap<>();
        map.put("userLogin", userLogin);
        map.put("result",result);
        return map;
    }

    @PutMapping("/{id:\\d+}")
    public Map update(@Valid @RequestBody UserLogin userLogin, BindingResult errors){
        if(errors.hasErrors())
            errors.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
        System.out.println(userLogin);
        int result = userService.update(userLogin);
        Map<String,Object> map  = new HashMap<>();
        map.put("userLogin", userLogin);
        map.put("result",result);

        return map;
    }

    @DeleteMapping("/{id:\\d+}")
    public int delete(@PathVariable Long id){
        return userService.delete(id);
    }

    @GetMapping
    @JsonView(UserLogin.UserSimpleView.class)
    public List<UserLogin> query(UserQueryCondition condition){
        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));
        return userService.findByCondition(condition);
    }
    @GetMapping("/{id:\\d+}")
    @JsonView(UserLogin.UserDetailView.class)
    public UserLogin getInfo(@PathVariable Long id){
        UserLogin userLogin = userService.findById(id);
        if(userLogin == null){
            throw new UserNotFoundException(id);
        }
        System.out.println(userLogin);
        return userLogin;
    }
}
