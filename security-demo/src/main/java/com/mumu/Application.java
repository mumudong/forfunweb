package com.mumu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TomcatEmbeddedServletContainerFactory
 * 默認掃描同包及子包
 */
@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello security";
    }

}
