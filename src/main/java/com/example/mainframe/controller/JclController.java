package com.example.mainframe.controller;

import com.example.mainframe.entity.JobOutputInforamtion;
import com.example.mainframe.service.JclService;
import com.example.mainframe.service.LoginService;
import com.example.mainframe.utils.Result;
import com.example.mainframe.utils.StatusCode;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class JclController {
    @Resource
    LoginService loginService;

    @Resource
    JclService jclService;

    @RequestMapping(value = "/submitjcl", method = RequestMethod.POST)
    public Result submitjcl(HttpSession session, String jcl){
        if(loginService.notLogin(session)){
            return new Result(true, StatusCode.ERROR,"not login");
        }
        List<JobOutputInforamtion> jobOutputInforamtions = jclService.submitJcl(session,jcl);
        if(jobOutputInforamtions != null){
            return new Result(true,StatusCode.OK,"submit successfully", jobOutputInforamtions);
        }
        return new Result(true,StatusCode.ERROR,"submit error");
    }
}
