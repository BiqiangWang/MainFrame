package com.example.mainframe.service;

import com.example.mainframe.entity.LoginInformation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface LoginService {
    //登陆
    String login(LoginInformation loginInformation , HttpSession session, HttpServletRequest requests);

    //注销
    void logoff(HttpSession session);

    //检查是否登陆
    boolean notLogin(HttpSession session);

    String islogin(HttpSession session, HttpServletRequest requests);
}
