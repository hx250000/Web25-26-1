package com.zjgsu.hx.quizserver.model;

import lombok.Data;

import java.util.List;

@Data
public class QSBeanOutManager {
    private int id;
    private String questionText;
    private List<String> options;
    private String answer;
}
