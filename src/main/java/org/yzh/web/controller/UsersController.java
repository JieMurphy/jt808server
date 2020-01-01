package org.yzh.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yzh.web.config.SessionKey;

import javax.servlet.http.HttpServletRequest;


@Controller
public class UsersController {
    private String controllerId = "admin";
    private String controllerPw = "admin";

    @CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
    @PostMapping(value = "/controller/login",consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String login(HttpServletRequest request)
    {
        String username = request.getParameter("username");
        String userpasswd = request.getParameter("password");

        if(username == null || userpasswd == null)
        {
            return "fail";
        }

        if(!username.equals(controllerId) || !userpasswd.equals(controllerPw))
        {
            return "fail";
        }
        System.out.println(username + userpasswd);
        request.getSession().setAttribute(SessionKey.USER_ID,username);
        return "success";
    }
}
