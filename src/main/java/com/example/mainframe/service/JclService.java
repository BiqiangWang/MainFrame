package com.example.mainframe.service;

import com.example.mainframe.entity.JobOutputInforamtion;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface JclService {

    List<JobOutputInforamtion> submitJcl(HttpSession session, String jcl);
}
