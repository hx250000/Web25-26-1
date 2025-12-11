package com.zjgsu.hx.quizserver.controller;

import com.zjgsu.hx.quizserver.common.ApiResponse;
import com.zjgsu.hx.quizserver.model.*;
import com.zjgsu.hx.quizserver.service.QuestionService;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {
    private QuestionService questionService;
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }
    @PostMapping("/addquestion")
    public ApiResponse<Question> addQuestion(@RequestBody QSBean qsBean) {
        return ApiResponse.success(questionService.addQuestion(qsBean));
    }
    @DeleteMapping("/deletequestion")
    public ApiResponse<Question> deleteQuestion(@RequestParam("id")int id) {
        return ApiResponse.success(questionService.deleteQuestionById(id));
    }
    @GetMapping("/allquestions")
    public ApiResponse<List<Question>> getAllQuestions() {
        return ApiResponse.success(questionService.findAll());
    }
    @GetMapping("/usergetquestions")
    public ApiResponse<List<QSBeanOut>> userGetQuestions() {
        return ApiResponse.success(questionService.getQuestions());
    }
    @GetMapping("/questions")
    public ApiResponse<QSBeanPage> getPage(@RequestParam(defaultValue="1")int page, @RequestParam(defaultValue="5")int pageSize){
        return ApiResponse.success(questionService.page(page, pageSize));
    }
    @GetMapping("/findquestion")
    public ApiResponse<List<QSBeanOutManager>> findQuestion(@RequestParam("keyword")String keyword) {
        return ApiResponse.success(questionService.findByName(keyword));
    }
    @PostMapping("/updatequestion")
    public ApiResponse<Question> updateQuestion(@RequestBody QSBeanUpdate qsBeanUpdate) {
        return ApiResponse.success(questionService.updateQuestion(qsBeanUpdate));
    }
}
