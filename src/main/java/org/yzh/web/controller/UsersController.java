package org.yzh.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yzh.web.config.SessionKey;
import org.yzh.web.database.orders.OrderService;
import org.yzh.web.database.orders.Orders;
import org.yzh.web.database.users.User;
import org.yzh.web.database.users.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class UsersController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


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

        if(userService.judge(username,userpasswd) == false || userService.findByNameAndPasswd(username,userpasswd).getPower() != User.管理员)
        {
            return "fail";
        }
        System.out.println(username + userpasswd);
        request.getSession().setAttribute(SessionKey.USER_ID,username);
        return "success";
    }

    @CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
    @PostMapping(value = "/controller/sign", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String sign(HttpServletRequest request)
    {
        User user1 = new User(request.getParameter("username"),request.getParameter("password"));



        if(userService.save(user1) == false)
        {
            return "用户已存在！！！";
        }

        return "注册成功！！！";
    }

    @CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
    @PostMapping(value = "/controller/allocation", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public List<Orders> allocat(HttpServletRequest request)
    {
        Orders orders = new Orders(1566,"ergre",555,"eferg");
        orderService.save(orders);
        System.out.println("分配了");
        return  orderService.findByStatus(Orders.待分配);
    }
}
