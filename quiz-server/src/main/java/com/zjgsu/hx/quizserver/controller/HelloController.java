package com.zjgsu.hx.quizserver.controller;

import com.zjgsu.hx.quizserver.common.ApiResponse;
import com.zjgsu.hx.quizserver.model.SimpleUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public ApiResponse<String> hello() {
        System.out.println("hello");
        return ApiResponse.success("Hello World");
    }

}
