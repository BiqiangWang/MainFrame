package com.example.mainframe.controller;


import org.springframework.web.bind.annotation.*;
import com.example.mainframe.service.LoginService;
import com.example.mainframe.entity.LoginInformation;
import com.example.mainframe.utils.Result;
import com.example.mainframe.utils.StatusCode;

import io.swagger.annotations.ApiOperation;


import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin
public class Logincontroller {
    @Resource
    private LoginService loginService;

    @ApiOperation(value = "login",notes = "login",tags = {"LoginController"})
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Result login(String account, String password, HttpSession session){
        if(loginService.notLogin(session)){
            LoginInformation loginInformation = new LoginInformation();
            loginInformation.setAccount(account);
            loginInformation.setPassword(password);
            String request = loginService.login(loginInformation,session);
            if (request.equals("successful")){
                return new Result(true,StatusCode.OK,"login successfully");
            }
            return new Result(true,StatusCode.ERROR,"login failed",request);
        }
        return new Result(true,StatusCode.OK,"login failed","You have already logged in");
    }

    @ApiOperation(value = "logoff",notes = "logoff", tags = {"LogoffController"})
    @RequestMapping(value = "/logoff",method = RequestMethod.DELETE)
    public Result logoff(HttpSession session){
        loginService.logoff(session);
        return new Result(true,StatusCode.OK,"logoff successfully");
    }

    @ApiOperation(value = "islogin",notes = "islogin", tags = {"IsloginController"})
    @RequestMapping(value = "/islogin",method = RequestMethod.DELETE)
    public Result islogin(HttpSession session){
        String ans = loginService.islogin(session);
        return new Result(true,StatusCode.OK,"get login info",ans);
    }

}
