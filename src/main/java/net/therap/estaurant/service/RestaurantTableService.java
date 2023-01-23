package net.therap.estaurant.service;

import net.therap.estaurant.dao.RestaurantTableDao;
import net.therap.estaurant.entity.RestaurantTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Service
public class RestaurantTableService {

    @Autowired
    public RestaurantTableDao restaurantTableDao;

    public List<RestaurantTable> findAll() {
        return restaurantTableDao.findAll();
    }

    public RestaurantTable findById(int id) {
        return restaurantTableDao.findById(id);
    }

    @Transactional
    public void delete(int id) throws Exception {
        restaurantTableDao.delete(id);
    }

    @Transactional
    public RestaurantTable saveOrUpdate(RestaurantTable restaurantTable) throws Exception {
        return restaurantTableDao.saveOrUpdate(restaurantTable);
    }
}
