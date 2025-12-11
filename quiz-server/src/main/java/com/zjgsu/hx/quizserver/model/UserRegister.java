package com.zjgsu.hx.quizserver.model;

import lombok.Data;

@Data
public class UserRegister {
    /** 用户名 */
    private String userName;

    /* 密码*/
    private String userPassword;

    private String checkPassword;

    /* 用户角色：0-普通用户，1-管理员 */
    private Integer userRole;
}
