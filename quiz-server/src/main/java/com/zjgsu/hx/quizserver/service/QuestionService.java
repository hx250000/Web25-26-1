package com.zjgsu.hx.quizserver.service;

import com.zjgsu.hx.quizserver.exception.ResourceNotFoundException;
import com.zjgsu.hx.quizserver.mapper.QuestionMapper;
import com.zjgsu.hx.quizserver.model.*;
import com.zjgsu.hx.quizserver.utils.Tools;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionMapper QSMapper;
    public QuestionService(QuestionMapper QSMapper) {
        this.QSMapper = QSMapper;
    }
    @Transactional
    public Question addQuestion(QSBean qsBean) {
        String ans = qsBean.getAnswer();
        if (!List.of("a", "b", "c", "d").contains(ans.toLowerCase())) {
            throw new IllegalArgumentException("Answer must be one of: a, b, c, or d");
        }
        Date date = new Date();

        Question q = new Question();
        q.setQuestionText(qsBean.getQuestion());

        q.setAnswer1Text(qsBean.getOptiona());
        q.setAnswer1Correct("a".equalsIgnoreCase(ans));

        q.setAnswer2Text(qsBean.getOptionb());
        q.setAnswer2Correct("b".equalsIgnoreCase(ans));

        q.setAnswer3Text(qsBean.getOptionc());
        q.setAnswer3Correct("c".equalsIgnoreCase(ans));

        q.setAnswer4Text(qsBean.getOptiond());
        q.setAnswer4Correct("d".equalsIgnoreCase(ans));

        q.setIsDelete(0);
        q.setCreateTime(date);
        q.setUpdateTime(date);
        QSMapper.insertQuestion(q);
        return q;
    }
    @Transactional
    public Question updateQuestion(QSBeanUpdate qsBeanUpdate) {
//        Question q = QSMapper.getQuestionById(qsBean.getId());
//        if (q == null) {
//            throw new ResourceNotFoundException("问题不存在");
//        }
        Question q = QSMapper.getQuestionById(qsBeanUpdate.getId());
        if (q == null) {
            throw new ResourceNotFoundException("问题未找到或已被删除！");
        }
        String ans = qsBeanUpdate.getAnswer().toLowerCase();
        if (!List.of("a","b","c","d").contains(ans)) {
            throw new IllegalArgumentException("Answer must be a/b/c/d");
        }

        q.setQuestionText(qsBeanUpdate.getQuestion());

        q.setAnswer1Text(qsBeanUpdate.getOptiona());
        q.setAnswer1Correct("a".equals(ans));

        q.setAnswer2Text(qsBeanUpdate.getOptionb());
        q.setAnswer2Correct("b".equals(ans));

        q.setAnswer3Text(qsBeanUpdate.getOptionc());
        q.setAnswer3Correct("c".equals(ans));

        q.setAnswer4Text(qsBeanUpdate.getOptiond());
        q.setAnswer4Correct("d".equals(ans));

        q.setUpdateTime(new Date());

        QSMapper.updateQuestion(q);
        return q;
    }


    @Transactional
    public Question deleteQuestionById(int questionId) {
        Question question=QSMapper.getQuestionById(questionId);
        if (question==null) {
            throw new ResourceNotFoundException("问题未找到或已被删除！");
        }
        QSMapper.deleteQuestion(questionId);
        question.setIsDelete(1);
        return question;
    }

    public List<Question> findAll(){
        return QSMapper.findAll();
    }

    public List<QSBeanOut> getQuestions(){
        //获取question list;
        List<Question> questionList = QSMapper.userGetQuestions();
        //将question list转化为QSBeanOut;
        List<QSBeanOut> qsBeanOutList = Tools.convertToQSBeanList(questionList);
        return qsBeanOutList;
    }

    public QSBeanPage page(int page, int pageSize){
        //获取总的记录数；
        int total=QSMapper.count();

        //获取分页查询结果列表；
        int start = (page-1)*pageSize;
        List<Question> questionList=QSMapper.page(start, pageSize);

        //将questionList转化为QSBeanOutManage
        //to be done;
        List<QSBeanOutManager> qsBeanOutManager = Tools.convertToQSBeanManagerList(questionList);

        //封装PageBean对象；
        QSBeanPage qsBeanPage = new QSBeanPage();
        qsBeanPage.setTotal(total);
        qsBeanPage.setQsBeanList(qsBeanOutManager);

        return qsBeanPage;
    }
    public List<QSBeanOutManager> findByName(String keyword){
        List<Question> questionList = QSMapper.findByName(keyword);
        List<QSBeanOutManager> qsBeanOutManager = Tools.convertToQSBeanManagerList(questionList);
        return qsBeanOutManager;
    }
}
