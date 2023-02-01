package net.therap.estaurant.service;

import net.bytebuddy.implementation.bytecode.Throw;
import net.therap.estaurant.dao.CategoryDao;
import net.therap.estaurant.entity.Category;
import net.therap.estaurant.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ItemService itemService;

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public Category findById(int id) {
        Category category = categoryDao.findById(id);

        if (Objects.isNull(category)) {
            throw new RuntimeException();
        }

        return category;
    }

    @Transactional
    public void delete(int id) throws Exception {
        Category category = categoryDao.findById(id);

        for(Item item: category.getItemList()){
            itemService.delete(item.getId());
        }

        categoryDao.delete(id);
    }

    @Transactional
    public Category saveOrUpdate(Category category) throws Exception {
        return categoryDao.saveOrUpdate(category);
    }
}
