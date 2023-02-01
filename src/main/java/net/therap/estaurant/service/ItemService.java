package net.therap.estaurant.service;

import net.therap.estaurant.dao.ItemDao;
import net.therap.estaurant.entity.Item;
import net.therap.estaurant.entity.OrderLineItem;
import net.therap.estaurant.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private OrderLineItemService orderLineItemService;

    public List<Item> findAvailable() {
        return itemDao.findAvailable();
    }

    public List<Item> findAll() {
        return itemDao.findAll();
    }

    public Item findById(int id) {
        Item item = itemDao.findById(id);

        if (Objects.isNull(item)) {
            throw new RuntimeException();
        }

        return item;
    }

    @Transactional
    public void delete(int id) throws Exception {
        Item item = itemDao.findById(id);

        for(OrderLineItem orderLineItem: item.getOrderLineItemList()){
            orderLineItemService.delete(orderLineItem.getId());
        }

        itemDao.delete(id);
    }

    @Transactional
    public Item saveOrUpdate(Item item) throws Exception {
        return itemDao.saveOrUpdate(item);
    }

    public boolean isDuplicateName(Item item) {
        List<Item> itemList = itemDao.findByName(item.getName());

        if (itemList.size() == 0) {
            return false;
        }

        return itemList.get(0).getId() != item.getId();
    }
}
