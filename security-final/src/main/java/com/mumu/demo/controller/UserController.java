package com.mumu.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.mumu.core.bean.UserLogin;
import com.mumu.core.bean.UserQueryCondition;
import com.mumu.core.properties.SecurityProperties;
import com.mumu.demo.exception.UserNotFoundException;
import com.mumu.core.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
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
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private ProviderSignInUtils providerSignInUtils;
//    @Autowired
//    private AppSignUpUtils appSignUpUtils;
    @Autowired
    private SecurityProperties securityProperties;

//    @PostMapping("/regist")
//    public void regist(UserLogin user, HttpServletRequest request){
//        String userId = user.getUsername();
////        providerSignInUtils.doPostSignUp(userId,new ServletWebRequest(request));
//        appSignUpUtils.doPostSignUp(new ServletWebRequest(request),userId);
//    }
    @GetMapping("/me/me")
    public Object getCurrentUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/me")
    public Object getCurUser(Authentication user,HttpServletRequest request) throws Exception{
        String token = StringUtils.substringAfter(request.getHeader("Authorization"),"bearer ");
        Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                                .parseClaimsJws(token).getBody();
        String extInfo = (String)claims.get("extInfo");
        logger.info("extInfo:{}",extInfo);
        return user;
    }

    @PostMapping
    public Map create(@Valid @RequestBody UserLogin userLogin /* BindingResult errors*/){
//        使用json字符串传递参数
/**        if(errors.hasErrors())
            errors.getAllErrors().stream().forEach(resources.error -> System.out.println(resources.error.getDefaultMessage()));*/
        logger.info(userLogin.toString());
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
        logger.info(userLogin.toString());
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
        logger.info(userLogin.toString());
        return userLogin;
    }
}
