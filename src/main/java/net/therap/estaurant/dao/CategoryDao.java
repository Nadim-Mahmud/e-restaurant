package net.therap.estaurant.dao;

import net.therap.estaurant.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Repository
public class CategoryDao extends AbstractDao<Category> {

    public Category isExistingCategory(Category category) {

        try {
            return entityManager.createNamedQuery("Category.isExistingCategory", Category.class)
                    .setParameter("id", category.getId())
                    .setParameter("name", category.getName())
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }


    @Override
    public List<Category> findAll() {
        return entityManager.createNamedQuery("Category.findAll", Category.class).getResultList();
    }

    @Override
    public Category findById(int id) {
        return entityManager.find(Category.class, id);
    }

    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(Category.class, id));
    }

    @Override
    public Category saveOrUpdate(Category category) throws Exception {

        if (category.isNew()) {
            entityManager.persist(category);
            entityManager.flush();
        } else {
            category = entityManager.merge(category);
        }

        return category;
    }
}
