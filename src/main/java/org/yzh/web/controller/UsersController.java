package org.yzh.web.controller;

import io.swagger.annotations.ApiOperation;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yzh.framework.TCPServerHandler;
import org.yzh.framework.session.Session;
import org.yzh.web.config.SessionKey;
import org.yzh.web.database.goods.GoodService;
import org.yzh.web.database.goods.Goods;
import org.yzh.web.database.influx.CodeInfo;
import org.yzh.web.database.orders.OrderService;
import org.yzh.web.database.orders.Orders;
import org.yzh.web.database.users.User;
import org.yzh.web.database.users.UserService;
import org.yzh.web.endpoint.JT808Endpoint;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
@RestController
public class UsersController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private GoodService goodService;

    @ApiOperation(value = "管理员登录")
    @PostMapping(value = "/controller/login",consumes = "application/x-www-form-urlencoded")
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

    @ApiOperation(value = "管理员注册")
    @PostMapping(value = "/controller/sign", consumes = "application/x-www-form-urlencoded")
    public String sign(HttpServletRequest request)
    {
        Goods goods = new Goods("香蕉",Goods.水果,12);
        Goods goods1 = new Goods("胡萝卜",Goods.蔬菜,13);
        Goods goods2 = new Goods("玉米",Goods.谷物,11);
        goodService.save(goods);
        goodService.save(goods1);
        goodService.save(goods2);

        Orders orders = new Orders("15268059209","香蕉",5,"南京金陵科技学院");
        Orders orders1 = new Orders("15268059209","香蕉",8,"钢铁人");
        Orders orders2 = new Orders("15268059209","玉米",3,"南京金陵科技学院");
        Orders orders3 = new Orders("15268059209","胡萝卜",2,"浙江衢州");
        orderService.save(orders);
        orderService.save(orders1);
        orderService.alterStatus(2,Orders.待确认,"15906709889");
        orderService.save(orders2);
        orderService.alterStatus(3,Orders.待签收);
        orderService.save(orders3);
        orderService.alterStatus(4,Orders.已签收);

        User user = new User("15906709889","jack","123","015850612084");
        User user1 = new User(request.getParameter("username"),request.getParameter("password"));
        User user2 = new User("15268059209","Zack","321");

        if(userService.save(user2) == false)
        {
            return "用户已存在！！！";
        }

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

    @ApiOperation(value = "管理员分配查询")
    @PostMapping(value = "/controller/allocation", consumes = "application/x-www-form-urlencoded")
    public List<Orders> allocation()
    {
        System.out.println("分配");
        return  orderService.findByStatus(Orders.待分配);
    }

    @ApiOperation(value = "管理员确认查询")
    @PostMapping(value = "/controller/confirm", consumes = "application/x-www-form-urlencoded")
    public List<Orders> confirm()
    {
        System.out.println("确认");
        return  orderService.findByStatus(Orders.待确认);
    }

    @ApiOperation(value = "管理员运输查询")
    @PostMapping(value = "/controller/transport", consumes = "application/x-www-form-urlencoded")
    public List<Orders> transport()
    {
        System.out.println("运送");
        return  orderService.findByStatus(Orders.待签收);
    }

    @ApiOperation(value = "管理员历史查询")
    @PostMapping(value = "/controller/history", consumes = "application/x-www-form-urlencoded")
    public List<Orders> hository()
    {
        System.out.println("完成");
        return  orderService.findByStatus(Orders.已签收);
    }

    @ApiOperation(value = "管理员终端信息查询")
    @PostMapping(value = "/controller/terminals", consumes = "application/x-www-form-urlencoded")
    public List<Session> terminals()
    {
        List<Session> lists =  TCPServerHandler.sessionManager.toList();
        for(Session session : lists)
        {
            User user = userService.findByTerNumber(session.getTerminalId());
            if(user != null)
            {
                session.setDriNumber(user.getNumber());
            }
        }
        return lists;
    }

    @ApiOperation(value = "管理员分配")
    @PostMapping(value = "/controller/assign", consumes = "application/x-www-form-urlencoded")
    public String assign(HttpServletRequest request) {
        Object number = request.getSession().getAttribute(SessionKey.USER_ID);
        if(userService.findByNumber(number.toString()).getPower() != User.管理员)
        {
            return "fail";
        }
        long id = Long.parseLong(request.getParameter("id"));
        String terNumber = request.getParameter("terminalId");
        User driver = userService.findByTerNumber(terNumber);
        if(driver == null)
        {
            return "fail";
        }
        /*if(orderService.isWorking(driver.getNumber()) || TCPServerHandler.sessionManager.getByMobileNumber(terNumber) == null)
        {
            return "fail";
        }*/
        if(orderService.alterStatus(id,Orders.待确认,driver.getNumber()) == false) {
            return "fail";
        }
        return "success";
    }

    @ApiOperation(value = "管理员查询位置")
    @PostMapping(value = "/controller/position", consumes = "application/x-www-form-urlencoded")
    public List<CodeInfo> position(HttpServletRequest request)
    {
        Object number = request.getSession().getAttribute(SessionKey.USER_ID);
        if(userService.findByNumber(number.toString()).getPower() != User.管理员)
        {
            return null;
        }
        String terNumber = request.getParameter("terminalId");
        QueryResult result = JT808Endpoint.influxDbUtils.queryByTernumberAndStatsLimitOne(terNumber,CodeInfo.在线);
        return JT808Endpoint.influxDbUtils.turn(result);
    }

    @ApiOperation(value = "消费者查询路线")
    @PostMapping(value = "/cumstomer/route", consumes = "application/x-www-form-urlencoded")
    public List<CodeInfo> cumstomer_route(HttpServletRequest request)
    {
        String id = request.getParameter("id");
        Orders orders = orderService.findOne(Long.parseLong(id));
        if(orders == null)
        {
            return null;
        }
        User user = userService.findByNumber(orders.getDriNumber());
        if(user == null)
        {
            return null;
        }
        QueryResult result = JT808Endpoint.influxDbUtils.queryByTernumberAndTime(user.getTerNumber(),orders.getStartTime());
        return JT808Endpoint.influxDbUtils.turn(result);
    }


    @ApiOperation(value = "司机确认")
    @PostMapping(value = "/driver/confirm", consumes = "application/x-www-form-urlencoded")
    public String confirmed(HttpServletRequest request)
    {
        //Object number = request.getSession().getAttribute(SessionKey.USER_ID);
        String driver = request.getParameter("terNumber");
        long id = Long.parseLong(request.getParameter("id"));
        if(orderService.findByDriverAndStatus(driver,Orders.待确认) == null || orderService.alterStatus(id,Orders.待签收) == false)
        {
            return "fail";
        }
        return  "success";
    }
}
