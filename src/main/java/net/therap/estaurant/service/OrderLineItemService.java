package net.therap.estaurant.service;

import net.therap.estaurant.dao.OrderLineItemDao;
import net.therap.estaurant.entity.OrderLineItem;
import net.therap.estaurant.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public boolean isItemOnProcess(int id){
        return orderLineDao.findByItemId(id).size() > 0;
    }

    public List<OrderLineItem> getOrderedNotificationByUserId(int id) {
        return orderLineDao.findOrderedItemByChef(userService.findById(id).getItemList());
    }

    public List<OrderLineItem> findAll() {
        return orderLineDao.findAll();
    }

    public OrderLineItem findById(int id) {
        return Optional.ofNullable(orderLineDao.findById(id)).orElseThrow(ResourceNotFoundException::new);
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
