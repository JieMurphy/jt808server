package org.yzh.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yzh.framework.TCPServerHandler;
import org.yzh.framework.session.Session;
import org.yzh.web.config.SessionKey;
import org.yzh.web.database.goods.GoodService;
import org.yzh.web.database.goods.Goods;
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

    @Autowired
    private GoodService goodService;


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
        request.getSession().setAttribute(SessionKey.USER_ID,userService.findByNameAndPasswd(username,userpasswd).getNumber());
        return "success";
    }

    @CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
    @PostMapping(value = "/controller/sign", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String sign(HttpServletRequest request)
    {
        Goods goods = new Goods("香蕉",Goods.水果,12);
        goodService.save(goods);

        Orders orders = new Orders("15906709889","香蕉",5,"南京金陵科技学院");
        orderService.save(orders);

        User user = new User("15906709889","jack","123","07777");
        User user1 = new User(request.getParameter("username"),request.getParameter("password"));

        if(userService.save(user1) == false)
        {
            return "用户已存在！！！";
        }

        if(userService.save(user) == false)
        {
            return "用户已存在！！！";
        }

        return "注册成功！！！";
    }

    @CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
    @PostMapping(value = "/controller/allocation", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public List<Orders> allocation()
    {
        System.out.println("分配");
        return  orderService.findByStatus(Orders.待分配);
    }

    @CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
    @PostMapping(value = "/controller/confirm", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public List<Orders> confirm()
    {
        System.out.println("确认");
        return  orderService.findByStatus(Orders.待确认);
    }

    @CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
    @PostMapping(value = "/controller/transport", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public List<Orders> transport()
    {
        System.out.println("运送");
        return  orderService.findByStatus(Orders.待签收);
    }

    @CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
    @PostMapping(value = "/controller/hository", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public List<Orders> hository()
    {
        System.out.println("完成");
        return  orderService.findByStatus(Orders.已签收);
    }

    @CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
    @PostMapping(value = "/controller/terminals", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public List<Session> terminals()
    {
        return TCPServerHandler.sessionManager.toList();
    }

    @CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
    @PostMapping(value = "/controller/assign", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String assign(HttpServletRequest request) {
        Object number = request.getSession().getAttribute(SessionKey.USER_ID);
        if (number == null || !(number instanceof String))
        {
            return "fail";
        }
        if(userService.findByNumber(number.toString()).getPower() != User.管理员)
        {
            return "fail";
        }
        long id = Long.parseLong(request.getParameter("id"));
        String terNumber = request.getParameter("terNumber");
        User driver = userService.findByTerNumber(terNumber);
        if(driver == null)
        {
            return "fail";
        }
        if(orderService.isWorking(driver.getNumber())/* || TCPServerHandler.sessionManager.getByMobileNumber(terNumber) == null*/)
        {
            return "fail";
        }
        if(orderService.alterStatus(id,Orders.待确认,driver.getNumber()) == false)
        {
            return "fail";
        }
        return "success";
    }

    @CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
    @PostMapping(value = "/driver/confirm", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    public String confirmed(HttpServletRequest request)
    {
        Object number = request.getSession().getAttribute(SessionKey.USER_ID);
        String driver = request.getParameter("terNumber");
        long id = Long.parseLong(request.getParameter("id"));
        if(orderService.findByDriverAndStatus(driver,Orders.待确认) == null || orderService.alterStatus(id,Orders.待签收) == false)
        {
            return "fail";
        }
        return  "success";
    }
}
