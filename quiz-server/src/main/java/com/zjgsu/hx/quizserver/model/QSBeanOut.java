package com.zjgsu.hx.quizserver.model;

import java.util.List;

public class QSBeanOut {
    private String question;
    private List<AnsBean> answers;

    public void setQuestion(String questionText) {
        this.question = questionText;
    }

    public void setAnswers(List<AnsBean> answers) {
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public List<AnsBean> getAnswers() {
        return answers;
    }
    //setter.getter.
}
