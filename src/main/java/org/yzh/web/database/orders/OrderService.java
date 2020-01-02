package org.yzh.web.database.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void save(Orders orders)
    {
        orderRepository.save(orders);
    }

    public List<Orders> findByStatus(int status)
    {
        return orderRepository.findByStatus(status);
    }

    public List<Orders> findByCustomerAndStatus(long number,int status)
    {
        return orderRepository.findByCumsNumberAndStatus(number,status);
    }

    public List<Orders> findByDriverAndStatus(long number,int status)
    {
        return orderRepository.findByDriNumberAndStatus(number,status);
    }

    public Orders findOne(long id)
    {
        return orderRepository.findOne(id);
    }

    public boolean isWorking(long number)
    {
        List<Orders> driver1 = orderRepository.findByDriNumberAndStatus(number,Orders.待确认);
        List<Orders> driver2 = orderRepository.findByDriNumberAndStatus(number,Orders.待签收);
        if(driver1 == null && driver2 == null)
        {
            return false;
        }
        return true;
    }

    public boolean alterStatus(long id,int status)
    {
        Orders orders = findOne(id);
        if(orders == null)
        {
            return false;
        }
        orders.setStatus(status);
        return true;
    }
}
