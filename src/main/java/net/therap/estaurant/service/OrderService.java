package net.therap.estaurant.service;

import net.therap.estaurant.dao.OrderDao;
import net.therap.estaurant.entity.Order;
import net.therap.estaurant.entity.Status;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    public List<Order> findByWaiterId(int waiterId) {
        return orderDao.findByWaiterId(waiterId);
    }

    public Order findById(int id) {
        Order order = orderDao.findById(id);

        if (Objects.isNull(order)) {
            throw new RuntimeException();
        }

        return order;
    }

    @Transactional
    public void delete(int id) throws Exception {
        orderDao.delete(id);
    }

    @Transactional
    public Order saveOrUpdate(Order order) throws Exception {
        return orderDao.saveOrUpdate(order);
    }

    public boolean tableExists(Order order) {

        List<Order> orderList = orderDao.findByTableId(order.getRestaurantTable().getId());

        if (orderList.size() == 0) {
            return false;
        }

        return orderList.get(0).getId() != order.getId();
    }

    public List<Order> getOrderListWithStatus(int waiterId) {
        List<Order> orderList = orderDao.findActiveOrderByWaiterId(waiterId);

        for (int i = 0; i < orderList.size(); i++) {

            int prepared = 0;
            int preparing = 0;
            int estTime = 0;

            for (int j = 0; j < orderList.get(i).getOrderLineItemList().size(); j++) {

                if (orderList.get(i).getOrderLineItemList().get(j).getStatus().equals(Status.PREPARED)) {
                    prepared++;
                }

                if (orderList.get(i).getOrderLineItemList().get(j).getStatus().equals(Status.PREPARING)) {
                    preparing++;
                }

                if (orderList.get(i).getOrderLineItemList().get(j).getStatus().equals(Status.PREPARING)) {
                    Date acceptedAt = orderList.get(i).getOrderLineItemList().get(j).getAcceptedAt();
                    Long elapsed = new Date().getTime() - acceptedAt.getTime();
                    elapsed = elapsed / 60000;
                    int time = (int) Math.max(0, orderList.get(i).getOrderLineItemList().get(j).getEstCookingTime() - elapsed);
                    estTime = Math.max(estTime, time);
                }

            }

            orderList.get(i).setEstTime(estTime);

            if (prepared == orderList.get(i).getOrderLineItemList().size()) {
                orderList.get(i).setStatus(Status.PREPARED);
                orderList.get(i).setEstTime(0);
            } else if (preparing > 0) {
                orderList.get(i).setStatus(Status.PREPARING);
            } else {
                orderList.get(i).setStatus(Status.ORDERED);
            }
        }

        return orderList;
    }
}
