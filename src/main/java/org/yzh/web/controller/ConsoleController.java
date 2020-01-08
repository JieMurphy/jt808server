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
        return "forward:/index.html";
    }

    @GetMapping(value = "/login")
    public String login(HttpSession session) {
        session.setAttribute(SessionKey.USER_ID, session.getId().hashCode());
        return "forward:/Login.html";
    }

    @GetMapping("test/{terminalId}")
    @ResponseBody
    public CommonResult updateParameters(@PathVariable("terminalId") String terminalId, @RequestParam String hex) {
        CommonResult response = (CommonResult) endpoint.send(terminalId, hex);
        return response;
    }

    @GetMapping(value = "/home")
    public String welcome(HttpSession session) throws Exception
    {
        System.out.println(session.getId().hashCode());
        return "forward:/homepage.html";
    }

    @GetMapping(value = "/allocation")
    public String allocation()
    {
        return "forward:/Allocation.html";
    }

    @GetMapping(value = "/confirm")
    public String confirm()
    {
        return "forward:/recv.html";
    }

    @GetMapping(value = "/sign")
    public String sign()
    {
        return "forward:/registe.html";
    }

    @GetMapping(value = "/histories")
    public String history()
    {
        return "forward:/history.html";
    }

    @GetMapping(value = "/welocome")
    public String welcome()
    {
        return "forward:/welcome.html";
    }
}