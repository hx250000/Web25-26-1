package com.zjgsu.hx.quizserver.service;

import com.zjgsu.hx.quizserver.exception.ResourceConflictException;
import com.zjgsu.hx.quizserver.exception.ResourceNotFoundException;
import com.zjgsu.hx.quizserver.mapper.UserMapper;
import com.zjgsu.hx.quizserver.model.PageBean;
import com.zjgsu.hx.quizserver.model.User;
import com.zjgsu.hx.quizserver.model.UserLogin;
import com.zjgsu.hx.quizserver.model.UserRegister;
import com.zjgsu.hx.quizserver.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Transactional
    public User addUser(UserRegister user) {
        UserRegister toAdd=user;
        String userName = toAdd.getUserName();
        String userPassword = toAdd.getUserPassword();
        String checkPassword = toAdd.getCheckPassword();
        int role=toAdd.getUserRole();

        checkInformation(userName,userPassword,checkPassword);
        boolean userExists=!(userMapper.getUserByName(userName)==null);
        if(userExists){
            throw new ResourceConflictException("用户名已存在！");
        }

        String passwordEncrypted=this.EncryptPassword(userPassword);
        Date now=new Date();

        User newUser=new User();
        newUser.setUserName(userName);
        newUser.setUserPassword(passwordEncrypted);
        newUser.setUserRole(role);
        newUser.setIsDelete(0);
        newUser.setCreateTime(now);
        newUser.setUpdateTime(now);

        userMapper.addUser(newUser);

        User userBack=newUser;
        userBack.setUserPassword("********");
        return userBack;
    }

    @Transactional
    public User deleteUserById(Long id) {
        User toDelete=userMapper.getUserById(id);
        if(toDelete==null){
            throw new ResourceNotFoundException("用户未找到！");
        }
        userMapper.deleteUserById(id);
        toDelete.setIsDelete(1);
        return toDelete;
    }

    @Transactional
    public User deleteUserByName(String userName) {
        User toDelete=userMapper.getUserByName(userName);
        if(toDelete==null){
            throw new ResourceNotFoundException("用户未找到！");
        }
        userMapper.deleteUserById(toDelete.getId());
        toDelete.setIsDelete(1);
        return toDelete;
    }

    @Transactional
    public User resetPassword(String userName, String newPassword) {
        User toReset=userMapper.getUserByName(userName);
        if(toReset==null){
            throw new ResourceNotFoundException("用户不存在！");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("new password can not be null！");
        }
        String newPasswordEncrypted=this.EncryptPassword(newPassword);
        toReset.setUserPassword(newPasswordEncrypted);
        toReset.setUpdateTime(new Date());
        userMapper.updateUser(toReset);
        toReset.setUserPassword("********");
        return toReset;
    }

    public List<User> findByName(String keyword){
        List<User> userList=userMapper.findByUserNameKeyword(keyword);
        for(User user:userList){
            user.setUserPassword("********");
        }
        return userList;
    }

    public PageBean page(int page, int pageSize){
        //获取总的记录数；
        int total=userMapper.count();

        //获取分页查询结果列表；
        int start = (page-1)*pageSize;
        List<User> userList=userMapper.page(start, pageSize);

        for (User user : userList) {
            user.setUserPassword("********");
        }

        //封装PageBean对象；
        PageBean pageBean = new PageBean();
        pageBean.setTotal(total);
        pageBean.setRows(userList);

        return pageBean;
    }

    public String login(UserLogin user){
        String userName=user.getUsername();
        String password=user.getPassword();
        this.checkInformation(userName,password,password);
        String encryptPassword=this.EncryptPassword(password);
        User userBack=userMapper.getUserByNameAndPassword(userName,encryptPassword);
        if(userBack==null){
            throw new IllegalArgumentException("用户名或密码不正确！");
        }
        Claims claims = Jwts.claims();
        claims.put("id", userBack.getId());
        claims.put("username", userBack.getUserName());

        String token = JwtUtils.generateTokenWithClaims(claims);
        return token;
    }

    public String adminlogin(UserLogin user){
        String userName=user.getUsername();
        String password=user.getPassword();
        this.checkInformation(userName,password,password);
        String encryptPassword=this.EncryptPassword(password);
        User userBack=userMapper.getUserByNameAndPassword(userName,encryptPassword);
        if(userBack==null){
            throw new IllegalArgumentException("用户名或密码不正确！");
        }
        if(userBack.getUserRole()!=1){
            throw new IllegalArgumentException("非管理员禁止登录管理端！");
        }
        Claims claims = Jwts.claims();
        claims.put("id", userBack.getId());
        claims.put("username", userBack.getUserName());
        claims.put("role", "admin");

        String token = JwtUtils.generateTokenWithClaims(claims);
        return token;
    }

    public String EncryptPassword(String password) {
        String newPassword="";
        //对密码进行加密;
        final String SALT = "com.quiz";
        newPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());
        return newPassword;
    }

    public void checkInformation(String userName, String userPassword, String checkPassword){
        if (userName==null || userPassword==null || checkPassword==null || userPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("username or password is null!");
        }
//        boolean userExists=!(userMapper.getUserByName(userName)==null);
//        if(userExists){
//            throw new ResourceConflictException("用户名已存在！");
//        }
        boolean passwordCorrect=checkPassword.equals(userPassword);
        if(!passwordCorrect){
            throw new IllegalArgumentException("confirmpassword is not equal to password！");
        }

        String regex = "^[a-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userName);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("username contains special character!");
        }
    }

}
