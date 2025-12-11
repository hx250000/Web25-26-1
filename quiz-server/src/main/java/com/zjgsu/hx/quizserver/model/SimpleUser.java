package com.zjgsu.hx.quizserver.model;

import lombok.Data;

@Data
public class SimpleUser {
    //private Long id;
    private String name;
    private Integer age;
    //private String email;

    public SimpleUser(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
}