package com.zjgsu.hx.quizserver.model;

import lombok.Data;

import java.util.List;

@Data
public class QSBeanPage {
    private int total;
    private   List<QSBeanOutManager> qsBeanList;
}
