package net.therap.estaurant.service;

import net.therap.estaurant.dao.ItemDao;
import net.therap.estaurant.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    public List<Item> findAll() {
        return itemDao.findAll();
    }

    public Item findById(int id) {
        return itemDao.findById(id);
    }

    @Transactional
    public void delete(int id) throws Exception {
        itemDao.delete(id);
    }

    @Transactional
    public Item saveOrUpdate(Item item) throws Exception {
        return itemDao.saveOrUpdate(item);
    }
}
