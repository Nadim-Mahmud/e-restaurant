package net.therap.estaurant.service;

import net.therap.estaurant.dao.OrderDao;
import net.therap.estaurant.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public List<Order> findAll() {
        return orderDao.findAll();
    }

    public Order findById(int id) {
        return orderDao.findById(id);
    }

    @Transactional
    public void delete(int id) throws Exception {
        orderDao.delete(id);
    }

    @Transactional
    public Order saveOrUpdate(Order order) throws Exception {
        return orderDao.saveOrUpdate(order);
    }
}
