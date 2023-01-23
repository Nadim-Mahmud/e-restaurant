package net.therap.estaurant.service;

import net.therap.estaurant.dao.CategoryDao;
import net.therap.estaurant.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public Category findById(int id) {
        return categoryDao.findById(id);
    }

    @Transactional
    public void delete(int id) throws Exception {
        categoryDao.delete(id);
    }

    @Transactional
    public Category saveOrUpdate(Category category) throws Exception {
        return categoryDao.saveOrUpdate(category);
    }
}
