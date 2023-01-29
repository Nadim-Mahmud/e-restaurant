package net.therap.estaurant.dao;

import net.therap.estaurant.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Repository
public class OrderDao extends AbstractDao<Order> {

    public List<Order> findByTableId(int id) {
        return entityManager.createNamedQuery("Order.findOrderByTable", Order.class)
                .setParameter("tableId", id)
                .getResultList();
    }

    public List<Order> findByWaiterId(int waiterId) {
        return entityManager.createNamedQuery("Order.findByWaiterId", Order.class)
                .setParameter("waiterId", waiterId)
                .getResultList();
    }

    public List<Order> findActiveOrderByWaiterId(int waiterId) {
        return entityManager.createNamedQuery("Order.findActiveOnly", Order.class)
                .setParameter("waiterId", waiterId)
                .getResultList();
    }

    @Override
    public List<Order> findAll() {
        return entityManager.createNamedQuery("Order.findAll", Order.class).getResultList();
    }

    @Override
    public Order findById(int id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(Order.class, id));
    }

    @Override
    public Order saveOrUpdate(Order order) throws Exception {

        if (order.isNew()) {
            entityManager.persist(order);
            entityManager.flush();
        } else {
            order = entityManager.merge(order);
        }

        return order;
    }
}
