package com.zjgsu.hx.quizserver.utils;

import com.zjgsu.hx.quizserver.model.AnsBean;
import com.zjgsu.hx.quizserver.model.QSBeanOut;
import com.zjgsu.hx.quizserver.model.QSBeanOutManager;
import com.zjgsu.hx.quizserver.model.Question;

import java.util.ArrayList;
import java.util.List;

public class Tools {
    public static List<QSBeanOut> convertToQSBeanList(List<Question> questionList) {
        List<QSBeanOut> result = new ArrayList<>();

        for (Question question : questionList) {
            QSBeanOut qsBean = new QSBeanOut();
            qsBean.setQuestion(question.getQuestionText());

            List<AnsBean> answers = new ArrayList<>();
            answers.add(new AnsBean(question.getAnswer1Text(), question.getAnswer1Correct()));
            answers.add(new AnsBean(question.getAnswer2Text(), question.getAnswer2Correct()));
            answers.add(new AnsBean(question.getAnswer3Text(), question.getAnswer3Correct()));
            answers.add(new AnsBean(question.getAnswer4Text(), question.getAnswer4Correct()));

            qsBean.setAnswers(answers);
            result.add(qsBean);
        }
        return result;
    }

    public static List<QSBeanOutManager> convertToQSBeanManagerList(List<Question> questionList) {
        List<QSBeanOutManager> result = new ArrayList<>();
        if (questionList == null) {
            return result;
        }

        for (Question question : questionList) {
            // 跳过已删除的问题
            if (question.getIsDelete() != null && question.getIsDelete() == 1) {
                continue;
            }

            QSBeanOutManager bean = new QSBeanOutManager();
            bean.setQuestionText(question.getQuestionText());

            // 构建选项列表（固定4个选项）
            List<String> options = new ArrayList<>(4);
            options.add(question.getAnswer1Text() != null ? question.getAnswer1Text() : "");
            options.add(question.getAnswer2Text() != null ? question.getAnswer2Text() : "");
            options.add(question.getAnswer3Text() != null ? question.getAnswer3Text() : "");
            options.add(question.getAnswer4Text() != null ? question.getAnswer4Text() : "");
            bean.setOptions(options);

            // 查找正确答案文本
            bean.setAnswer(determineCorrectAnswer(question));
            bean.setQuestionText(question.getQuestionText());//设置id;
            bean.setId(question.getId());
            result.add(bean);
        }

        return result;
    }

    private static String determineCorrectAnswer(Question question) {
        if (question.getAnswer1Correct() != null && question.getAnswer1Correct()) {
            return question.getAnswer1Text() != null ? question.getAnswer1Text() : "";
        }
        if (question.getAnswer2Correct() != null && question.getAnswer2Correct()) {
            return question.getAnswer2Text() != null ? question.getAnswer2Text() : "";
        }
        if (question.getAnswer3Correct() != null && question.getAnswer3Correct()) {
            return question.getAnswer3Text() != null ? question.getAnswer3Text() : "";
        }
        if (question.getAnswer4Correct() != null && question.getAnswer4Correct()) {
            return question.getAnswer4Text() != null ? question.getAnswer4Text() : "";
        }
        return ""; // 没有正确答案时返回空字符串
    }
}
