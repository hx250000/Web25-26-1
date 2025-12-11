package com.zjgsu.hx.quizserver.mapper;

import com.zjgsu.hx.quizserver.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    public List<User> findAll();

    @Insert("insert into user(userName, userPassword,isDelete,userRole,createTime, updateTime)"+
            "values(#{userName}, #{userPassword}, #{isDelete}, #{userRole}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int addUser(User user);

    @Select("select * from user where userName LIKE CONCAT('%', #{keyword}, '%') and isDelete = 0")
    public List<User> findByUserNameKeyword(String keyword);

    @Update("UPDATE user SET isDelete = 1, updateTime = NOW() WHERE id = #{id}")
    public int deleteUserById(Long id);

    @Update("UPDATE user SET " +
            "userName = #{userName}, " +
            "userPassword = #{userPassword}, " +
            "isDelete = #{isDelete}, " +
            "userRole = #{userRole}, " +
            "updateTime = #{updateTime} " +
            "WHERE id = #{id}")
    public int updateUser(User user);

    @Select("select * from user where userName=#{userName} and isDelete=0")
    public User getUserByName(String userName);

    @Select("select * from user where id = #{id} and isDelete = 0")
    public User getUserById(Long id);

    @Select("SELECT COUNT(*) FROM user WHERE isDelete=0")
    public int count();

    @Select("SELECT * FROM user WHERE isDelete=0 limit #{start},#{pageSize}")
    public List<User> page(Integer start, Integer pageSize);

    @Select("SELECT * FROM user where userName=#{userName} and userPassword=#{password}")
    User getUserByNameAndPassword(String userName, String password);
}
