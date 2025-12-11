package com.zjgsu.hx.quizserver.mapper;

import com.zjgsu.hx.quizserver.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {


    @Insert("INSERT INTO questions (questionText,answer1Text, answer1Correct,answer2Text, answer2Correct, answer3Text, answer3Correct,answer4Text, answer4Correct, isDelete, createTime, updateTime)" +
            "VALUES (#{questionText},#{answer1Text}, #{answer1Correct}, #{answer2Text}, #{answer2Correct},#{answer3Text}, #{answer3Correct},#{answer4Text}, #{answer4Correct}, #{isDelete},#{createTime},#{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertQuestion(Question question);

    @Update("UPDATE questions SET isDelete = 1, updateTime = NOW() WHERE id = #{id}")
    int deleteQuestion(int id);

    @Select("SELECT * FROM questions WHERE id=#{questionId} AND isDelete=0")
    Question getQuestionById(int questionId);

    @Select("SELECT * FROM questions WHERE isDelete=0")
    List<Question> findAll();

    @Select("SELECT * FROM questions where isDelete=0 ORDER BY RAND() LIMIT 5")
    List<Question> userGetQuestions();

    @Select("SELECT COUNT(*) FROM questions WHERE isDelete=0")
    int count();

    @Select("SELECT * FROM questions WHERE isDelete=0 limit #{start},#{pageSize}")
    List<Question> page(int start, int pageSize);

    @Select("SELECT * FROM questions WHERE questionText LIKE CONCAT('%', #{keyword}, '%') AND isDelete=0")
    List<Question> findByName(String keyword);

    @Update("UPDATE questions SET " +
            "questionText = #{questionText}, " +
            "answer1Text = #{answer1Text}, " +
            "answer1Correct = #{answer1Correct}, " +
            "answer2Text = #{answer2Text}, " +
            "answer2Correct = #{answer2Correct}, " +
            "answer3Text = #{answer3Text}, " +
            "answer3Correct = #{answer3Correct}, " +
            "answer4Text = #{answer4Text}, " +
            "answer4Correct = #{answer4Correct}, " +
            "updateTime = #{updateTime} " +
            "WHERE id = #{id}")
    int updateQuestion(Question question);

}
