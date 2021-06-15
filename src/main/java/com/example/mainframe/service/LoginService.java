package com.example.mainframe.service;

import com.example.mainframe.entity.LoginInformation;

import javax.servlet.http.HttpSession;

public interface LoginService {
    //登陆
    String login(LoginInformation loginInformation , HttpSession session);

    //注销
    void logoff(HttpSession session);

    //检查是否登陆
    boolean notLogin(HttpSession session);

    String islogin(HttpSession session);
}
