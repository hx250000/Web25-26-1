package com.zjgsu.hx.quizserver.controller;

import com.zjgsu.hx.quizserver.common.ApiResponse;
import com.zjgsu.hx.quizserver.model.PageBean;
import com.zjgsu.hx.quizserver.model.User;
import com.zjgsu.hx.quizserver.model.UserLogin;
import com.zjgsu.hx.quizserver.model.UserRegister;
import com.zjgsu.hx.quizserver.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/Register")
    public ApiResponse<User> Register(@RequestBody UserRegister userRegister){
        System.out.println("Register 接收到的 userRegister = \n" + userRegister);
        return ApiResponse.success(userService.addUser(userRegister));
    }

    @PostMapping("/adduser")
    public ApiResponse<User> AddUser(@RequestBody UserRegister userRegister){
        return ApiResponse.success(userService.addUser(userRegister));
    }

    @PostMapping("/userlogin")
    public ApiResponse<String> userLogin(@RequestBody UserLogin user){
        return ApiResponse.success(userService.login(user));
    }

    @PostMapping("/adminlogin")
    public ApiResponse<String> Login(@RequestBody UserLogin user){
        return ApiResponse.success(userService.adminlogin(user));
    }

    @DeleteMapping("/DeleteById")
    public ApiResponse<User> DeleteById(@RequestParam("id") Long id) {
        return ApiResponse.success(userService.deleteUserById(id));
    }

    @DeleteMapping("/DeleteByName")
    public ApiResponse<User> DeleteByName(@RequestParam("name") String name) {
        return ApiResponse.success(userService.deleteUserByName(name));
    }

    @PutMapping("/ResetPassword")
    public ApiResponse<User> ResetPassword(@RequestParam("userName") String name,@RequestParam("newPassword") String newPassword) {
        return ApiResponse.success(userService.resetPassword(name,newPassword));
    }

    @GetMapping("/FindUsers")
    public ApiResponse<List<User>> FindUsers(@RequestParam("keyword")String key) {
        return ApiResponse.success(userService.findByName(key));
    }

    @GetMapping("/users")
    public ApiResponse<PageBean> getPage(@RequestParam(defaultValue="1")Integer page, @RequestParam(defaultValue="5")Integer pageSize){
        PageBean pageBean=userService.page(page, pageSize);
        return ApiResponse.success(pageBean);
    }

}
