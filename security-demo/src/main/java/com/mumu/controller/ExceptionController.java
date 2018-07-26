package com.mumu.controller;

import com.mumu.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,Object> handlerUserNotFoundException(UserNotFoundException exception){
        Map<String,Object> result = new HashMap<>();
        result.put("id",exception.getId());
        result.put("message",exception.getMessage());
        return result;
    }
}
