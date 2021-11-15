package com.zja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * http://127.0.0.1:8082/web-b/get
 */
@RestController
@SpringBootApplication
public class BWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BWebApplication.class, args);
    }

    /**
     * 方式一：注解 @CrossOrigin 解决跨域
     */
//    @CrossOrigin
    @GetMapping("/get")
    public Object get(){
        return "get-b";
    }
}
