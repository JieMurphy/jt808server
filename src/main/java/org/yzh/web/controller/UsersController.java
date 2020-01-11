package org.yzh.web.controller;

import io.swagger.annotations.ApiOperation;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yzh.framework.TCPServerHandler;
import org.yzh.framework.log.Logger;
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

    @Autowired
    private Logger logger;

    @ApiOperation(value = "管理员登录")
    @PostMapping(value = "/controller/login",consumes = "application/x-www-form-urlencoded")
    public String login(HttpServletRequest request)
    {
        String username = request.getParameter("username");
        String userpasswd = request.getParameter("password");

        logger.logUsers("管理员登录",username + " " + userpasswd);

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
        User user = new User("15906709889","jack","123","015850612084");
        User user1 = new User(request.getParameter("username"),request.getParameter("password"));
        User user2 = new User("15268059209","Zack","321");

        logger.logUsers("管理员登录",user1.getUsername() + " " + user1.getPassword());

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

        Goods goods = new Goods("香蕉",Goods.水果,12);
        Goods goods1 = new Goods("胡萝卜",Goods.蔬菜,13);
        Goods goods2 = new Goods("玉米",Goods.谷物,11);
        Goods goods3 = new Goods("火龙果",Goods.水果,10);
        Goods goods4 = new Goods("花菜",Goods.蔬菜,15);
        Goods goods5 = new Goods("小麦",Goods.谷物,17);
        Goods goods6= new Goods("苹果",Goods.水果,9);
        Goods goods7 = new Goods("白菜",Goods.蔬菜,16);
        Goods goods8 = new Goods("水稻",Goods.谷物,8);
        goodService.save(goods);
        goodService.save(goods1);
        goodService.save(goods2);
        goodService.save(goods3);
        goodService.save(goods4);
        goodService.save(goods5);
        goodService.save(goods6);
        goodService.save(goods7);
        goodService.save(goods8);

        return "注册成功！！！";
    }

    @ApiOperation(value = "管理员分配查询")
    @PostMapping(value = "/controller/allocation", consumes = "application/x-www-form-urlencoded")
    public List<Orders> allocation()
    {
        logger.logUsers("管理员分配查询","");
        return  orderService.findByStatus(Orders.待分配);
    }

    @ApiOperation(value = "管理员确认查询")
    @PostMapping(value = "/controller/confirm", consumes = "application/x-www-form-urlencoded")
    public List<Orders> confirm()
    {
        logger.logUsers("管理员确认查询","");
        return  orderService.findByStatus(Orders.待确认);
    }

    @ApiOperation(value = "管理员运输查询")
    @PostMapping(value = "/controller/transport", consumes = "application/x-www-form-urlencoded")
    public List<Orders> transport()
    {
        logger.logUsers("管理员运输查询","");
        return  orderService.findByStatus(Orders.待签收);
    }

    @ApiOperation(value = "管理员历史查询")
    @PostMapping(value = "/controller/history", consumes = "application/x-www-form-urlencoded")
    public List<Orders> hository()
    {
        logger.logUsers("管理员历史查询","");
        return  orderService.findByStatus(Orders.已签收);
    }

    @ApiOperation(value = "管理员终端信息查询")
    @PostMapping(value = "/controller/terminals", consumes = "application/x-www-form-urlencoded")
    public List<Session> terminals()
    {
        logger.logUsers("管理员终端信息查询","");
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
        logger.logUsers("管理员分配","");
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
        if(orderService.isWorking(driver.getNumber()) || TCPServerHandler.sessionManager.getByMobileNumber(terNumber) == null)
        {
            return "fail";
        }
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

        logger.logUsers("管理员查询位置",terNumber);

        QueryResult result = JT808Endpoint.influxDbUtils.queryByTernumberAndStatsLimitOne(terNumber,CodeInfo.在线);
        return JT808Endpoint.influxDbUtils.turn(result);
    }

    @ApiOperation(value = "消费者登录")
    @PostMapping(value = "/customer/login", consumes = "application/x-www-form-urlencoded")
    public String customer_login(HttpServletRequest request)
    {
        String name = request.getParameter("userid");
        String passwd = request.getParameter("passwd");

        logger.logUsers("消费者登录",name + " " + passwd);

        User user = userService.findByNameAndPasswd(name,passwd);
        if(user == null || user.getPower() != User.消费者)
        {
            return "fail";
        }
        request.getSession().setAttribute(SessionKey.USER_ID,userService.findByNameAndPasswd(name,passwd).getNumber());
        return "success";
    }

    @ApiOperation(value = "消费者注册")
    @PostMapping(value = "/customer/sign", consumes = "application/x-www-form-urlencoded")
    public String customer_sign(HttpServletRequest request)
    {
        String name = request.getParameter("userid");
        String passwd = request.getParameter("passwd");
        String number = request.getParameter("tel");

        logger.logUsers("消费者注册",name + " " + passwd + " " + number);

        User user = new User(number,name,passwd);
        if(userService.save(user) == false)
        {
            return "fail";
        }
        return "success";
    }

    @ApiOperation(value = "消费者查询路线")
    @PostMapping(value = "/customer/route", consumes = "application/x-www-form-urlencoded")
    public List<CodeInfo> customer_route(HttpServletRequest request)
    {
        String id = request.getParameter("id");

        logger.logUsers("消费者查询路线",id);

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
        QueryResult result = JT808Endpoint.influxDbUtils.queryByTernumberAndTime(user.getTerNumber(),orders.getChangeTime());
        return JT808Endpoint.influxDbUtils.turn(result);
    }

    @ApiOperation(value = "消费者查询商品")
    @PostMapping(value = "/customer/goods", consumes = "application/x-www-form-urlencoded")
    public List<Goods> customer_goods(HttpServletRequest request)
    {
        String kind = request.getParameter("kind");

        logger.logUsers("消费者查询商品",kind);

        if(kind == null)
        {
            return null;
        }
        return goodService.findByKind(Integer.parseInt(kind));
    }

    @ApiOperation(value = "消费者下单")
    @PostMapping(value = "/customer/buy", consumes = "application/x-www-form-urlencoded")
    public String customer_buy(HttpServletRequest request)
    {
        String goodName = request.getParameter("goodName");

        logger.logUsers("消费者下单",goodName);

        if(goodService.judge(goodName) == false)
        {
            return "fail";
        }
        String count = request.getParameter("amount");
        String address0 = request.getParameter("addrpicker0");
        String address1 = request.getParameter("addrpicker1");
        String address2 = request.getParameter("addrpicker2");
        String addr = request.getParameter("addr");
        addr = address0 + address1 + address2 + addr;

        String tel = (String) request.getSession().getAttribute(SessionKey.USER_ID);

        Orders orders = new Orders(tel,goodName,Integer.parseInt(count),addr);
        orderService.save(orders);

        return "success";
    }

    @ApiOperation(value = "消费者查询待分配")
    @PostMapping(value = "/customer/wait", consumes = "application/x-www-form-urlencoded")
    public List<Orders> customer_wait(HttpServletRequest request)
    {
        String tel = (String) request.getSession().getAttribute(SessionKey.USER_ID);

        logger.logUsers("消费者查询待分配",tel);

        return orderService.findByCustomerAndStatus(tel,Orders.待分配);
    }

    @ApiOperation(value = "消费者查询待签收")
    @PostMapping(value = "/customer/transport", consumes = "application/x-www-form-urlencoded")
    public List<Orders> customer_transport(HttpServletRequest request)
    {
        String tel = (String) request.getSession().getAttribute(SessionKey.USER_ID);

        logger.logUsers("消费者查询待签收",tel);

        return orderService.findByCustomerAndStatus(tel,Orders.待签收);
    }

    @ApiOperation(value = "消费者查询已签收")
    @PostMapping(value = "/customer/history", consumes = "application/x-www-form-urlencoded")
    public List<Orders> customer_history(HttpServletRequest request)
    {
        String tel = (String) request.getSession().getAttribute(SessionKey.USER_ID);

        logger.logUsers("消费者查询已签收",tel);

        return orderService.findByCustomerAndStatus(tel,Orders.已签收);
    }

    @ApiOperation(value = "消费者签收")
    @PostMapping(value = "/customer/assign", consumes = "application/x-www-form-urlencoded")
    public String customer_assign(HttpServletRequest request)
    {
        String tel = (String)request.getSession().getAttribute(SessionKey.USER_ID);
        String id = request.getParameter("id");

        logger.logUsers("消费者签收",tel + " " + id);

        Orders orders = orderService.findOne(Integer.parseInt(id));
        if(orders == null || orders.getCumsNumber().equals(tel) == false)
        {
            return "fail";
        }
        orderService.alterStatus(orders.getId(),Orders.已签收);
        return "success";
    }

    @ApiOperation(value = "司机登录")
    @PostMapping(value = "/driver/login", consumes = "application/x-www-form-urlencoded")
    public String driver_login(HttpServletRequest request)
    {
        String name = request.getParameter("userid");
        String passwd = request.getParameter("passwd");

        logger.logUsers("司机登录",name + " " + passwd);

        User user = userService.findByNameAndPasswd(name,passwd);
        if(user == null || user.getPower() != User.司机)
        {
            return "fail";
        }
        request.getSession().setAttribute(SessionKey.USER_ID,userService.findByNameAndPasswd(name,passwd).getNumber());
        return "success";
    }

    @ApiOperation(value = "司机注册")
    @PostMapping(value = "/driver/sign", consumes = "application/x-www-form-urlencoded")
    public String driver_sign(HttpServletRequest request)
    {
        String name = request.getParameter("userid");
        String passwd = request.getParameter("passwd");
        String number = request.getParameter("tel");
        String terNumber = request.getParameter("terminalId");

        logger.logUsers("司机注册",name + " " + passwd + " " + number + " " + terNumber);

        if(userService.findByTerNumber(terNumber) != null)
        {
            return "fail";
        }
        User user = new User(number,name,passwd);
        if(userService.save(user) == false)
        {
            return "fail";
        }
        return "success";
    }

    @ApiOperation(value = "司机确认")
    @PostMapping(value = "/driver/confirm", consumes = "application/x-www-form-urlencoded")
    public String confirmed(HttpServletRequest request)
    {
        String number = (String) request.getSession().getAttribute(SessionKey.USER_ID);
        String answer = request.getParameter("answer");

        logger.logUsers("司机确认",number + answer);

        long id = Long.parseLong(request.getParameter("id"));
        Orders orders = orderService.findOne(id);
        if(orders == null || orders.getDriNumber().equals(number) == false)
        {
            return "fail";
        }

        if(answer.equals("refuse"))
        {
            if(orderService.alterStatus(id,Orders.待分配) == false)
            {
                return "fail";
            }
            return "success";
        }
        if(orderService.alterStatus(id,Orders.待签收) == false)
        {
            return "fail";
        }
        return  "success";
    }

    @ApiOperation(value = "司机查询任务")
    @PostMapping(value = "/driver/search", consumes = "application/x-www-form-urlencoded")
    public List<Orders> driver_search(HttpServletRequest request)
    {
        String number = (String)request.getSession().getAttribute(SessionKey.USER_ID);

        logger.logUsers("司机查询任务",number);

        return orderService.findByDriverAndStatus(number,Orders.待确认);
    }

    @ApiOperation(value = "司机查询运输中任务")
    @PostMapping(value = "/driver/transport", consumes = "application/x-www-form-urlencoded")
    public List<Orders> driver_transport(HttpServletRequest request)
    {
        String number = (String)request.getSession().getAttribute(SessionKey.USER_ID);

        logger.logUsers("司机查询运输中任务",number);

        return orderService.findByDriverAndStatus(number,Orders.待签收);
    }

    @ApiOperation(value = "司机查询已完成任务")
    @PostMapping(value = "/driver/history", consumes = "application/x-www-form-urlencoded")
    public List<Orders> driver_history(HttpServletRequest request)
    {
        String number = (String)request.getSession().getAttribute(SessionKey.USER_ID);

        logger.logUsers("司机查询已完成任务",number);

        return orderService.findByDriverAndStatus(number,Orders.已签收);
    }

    @ApiOperation(value = "司机查询在线时长")
    @PostMapping(value = "/driver/time", consumes = "application/x-www-form-urlencoded")
    public CodeInfo driver_time(HttpServletRequest request)
    {
        String number = (String)request.getSession().getAttribute(SessionKey.USER_ID);

        logger.logUsers("司机查询在线时长",number);

        User user = userService.findByNumber(number);
        if (user == null)
        {
            return null;
        }
        return JT808Endpoint.influxDbUtils.getIn20(user.getTerNumber());
    }

    @ApiOperation(value = "司机查询上线记录")
    @PostMapping(value = "/driver/online", consumes = "application/x-www-form-urlencoded")
    public List<CodeInfo> driver_online(HttpServletRequest request)
    {
        String number = (String)request.getSession().getAttribute(SessionKey.USER_ID);

        logger.logUsers("司机查询上线记录",number);

        User user = userService.findByNumber(number);
        if (user == null)
        {
            return null;
        }

        QueryResult queryResult = JT808Endpoint.influxDbUtils.queryByTernumberAndStats(user.getTerNumber(),CodeInfo.上线);

        return JT808Endpoint.influxDbUtils.turn(queryResult);
    }

    @ApiOperation(value = "司机查询下线记录")
    @PostMapping(value = "/driver/offline", consumes = "application/x-www-form-urlencoded")
    public List<CodeInfo> driver_offline(HttpServletRequest request)
    {
        String number = (String)request.getSession().getAttribute(SessionKey.USER_ID);

        logger.logUsers("司机查询下线记录",number);

        User user = userService.findByNumber(number);
        if (user == null)
        {
            return null;
        }

        QueryResult queryResult = JT808Endpoint.influxDbUtils.queryByTernumberAndStats(user.getTerNumber(),CodeInfo.下线);

        return JT808Endpoint.influxDbUtils.turn(queryResult);
    }
}
