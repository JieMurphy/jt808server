package org.yzh.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yzh.web.config.SessionKey;
import org.yzh.web.endpoint.JT808Endpoint;
import org.yzh.web.jt808.dto.CommonResult;

import javax.servlet.http.HttpSession;
//控制台控制器
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
@Controller
public class ConsoleController {

    @Autowired
    private JT808Endpoint endpoint;

    @GetMapping(value = "/")
    public String console(HttpSession session) {
        session.setAttribute(SessionKey.USER_ID, session.getId().hashCode());
        System.out.println(session.getId().hashCode());
        return "forward:/controller/Login.html";
    }

    @GetMapping("test/{terminalId}")
    @ResponseBody
    public CommonResult updateParameters(@PathVariable("terminalId") String terminalId, @RequestParam String hex) {
        CommonResult response = (CommonResult) endpoint.send(terminalId, hex);
        return response;
    }

    @GetMapping(value = "/success")
    public String welcome() throws Exception
    {
        return "forward:/controller/homepage.html";
    }
}