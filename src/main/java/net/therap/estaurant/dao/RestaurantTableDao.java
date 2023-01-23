package net.therap.estaurant.dao;

import net.therap.estaurant.entity.RestaurantTable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Repository
public class RestaurantTableDao extends AbstractDao<RestaurantTable> {

    @Override
    public List<RestaurantTable> findAll() {
        return entityManager.createNamedQuery("RestaurantTable.findAll", RestaurantTable.class).getResultList();
    }

    @Override
    public RestaurantTable findById(int id) {
        return entityManager.find(RestaurantTable.class, id);
    }

    @Override
    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(RestaurantTable.class, id));
    }

    @Override
    public RestaurantTable saveOrUpdate(RestaurantTable restaurantTable) throws Exception {

        if (restaurantTable.isNew()) {
            entityManager.persist(restaurantTable);
            entityManager.flush();
        } else {
            restaurantTable = entityManager.merge(restaurantTable);
        }

        return restaurantTable;
    }
}
