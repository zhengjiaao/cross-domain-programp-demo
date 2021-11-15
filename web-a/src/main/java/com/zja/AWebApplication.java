package com.zja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * http://127.0.0.1:8081/web-a/get
 */
@RestController
@RequestMapping
@SpringBootApplication
public class AWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AWebApplication.class, args);
    }

    @GetMapping("get")
    public Object get(HttpServletRequest request) {
        /**
         * http://127.0.0.1:18093/web-a/get  127.0.0.1
         * http://localhost:18093/web-a/get  127.0.0.1
         * http://192.168.2.128:18093/web-a/get  192.168.2.128
         */
        System.out.println("客户端IP: " + IPUtils.getIpAddr(request));
        return "get-a";
    }

    /**
     * http://127.0.0.1:8081/web-a/resource/rest/resource-catalogs?resourceCategory=6
     * @param resourceCategory
     */
    @GetMapping("/resource/rest/resource-catalogs")
    public Object get(HttpServletRequest request, @RequestParam String resourceCategory) {
        System.out.println("resourceCategory: " + resourceCategory);
        System.out.println("客户端IP: " + IPUtils.getIpAddr(request));
        Map map = new HashMap();
        map.put("status", 1);
        map.put("content", new ArrayList<>());
        map.put("message", IPUtils.getIpAddr(request));
        return map;
    }

}
