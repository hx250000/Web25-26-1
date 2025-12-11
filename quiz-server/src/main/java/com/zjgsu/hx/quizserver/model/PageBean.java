package com.zjgsu.hx.quizserver.model;

import lombok.Data;

import java.util.List;

@Data
public class PageBean {
    private int total;
    private List<User> rows;
}
