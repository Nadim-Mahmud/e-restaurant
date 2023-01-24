package net.therap.estaurant.dao;

import net.therap.estaurant.entity.OrderLineItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Repository
public class OrderLineItemDao extends AbstractDao<OrderLineItem> {

    @Override
    public List<OrderLineItem> findAll() {
        return entityManager.createNamedQuery("OrderLineItem.findAll", OrderLineItem.class).getResultList();
    }

    @Override
    public OrderLineItem findById(int id) {
        return entityManager.find(OrderLineItem.class, id);
    }

    @Override
    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(OrderLineItem.class, id));
    }

    @Override
    public OrderLineItem saveOrUpdate(OrderLineItem orderLineItem) throws Exception {

        if (orderLineItem.isNew()) {
            entityManager.persist(orderLineItem);
            entityManager.flush();
        } else {
            orderLineItem = entityManager.merge(orderLineItem);
        }

        return orderLineItem;
    }
}
