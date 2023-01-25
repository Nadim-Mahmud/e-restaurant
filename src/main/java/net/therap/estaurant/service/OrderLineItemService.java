package net.therap.estaurant.service;

import net.therap.estaurant.dao.OrderLineItemDao;
import net.therap.estaurant.entity.OrderLineItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/24/23
 */
@Service
public class OrderLineItemService {

    @Autowired
    private OrderLineItemDao orderLineDao;

    @Autowired
    private UserService userService;

    public List<OrderLineItem> getOrderedNotificationByUserId(int id){
        return orderLineDao.findOrderedItemByChef(userService.findById(id).getItemList());
    }

    public List<OrderLineItem> findAll() {
        return orderLineDao.findAll();
    }

    public OrderLineItem findById(int id) {
        return orderLineDao.findById(id);
    }

    @Transactional
    public void delete(int id) throws Exception {
        orderLineDao.delete(id);
    }

    @Transactional
    public OrderLineItem saveOrUpdate(OrderLineItem orderLineitem) throws Exception {
        return orderLineDao.saveOrUpdate(orderLineitem);
    }
}
