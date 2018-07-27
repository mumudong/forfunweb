package com.mumu.demo.service.impl;

import com.mumu.demo.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/7/25.
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String greeting(String name) {
        return "hello " + name;
    }

}
