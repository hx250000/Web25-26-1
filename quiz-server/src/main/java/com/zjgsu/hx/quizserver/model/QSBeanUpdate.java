package com.zjgsu.hx.quizserver.model;

import lombok.Data;

@Data
public class QSBeanUpdate {
    private Integer id;      // 题目ID（必须）
    private String question;
    private String optiona;
    private String optionb;
    private String optionc;
    private String optiond;
    private String answer; // "a","b","c","d"
}
