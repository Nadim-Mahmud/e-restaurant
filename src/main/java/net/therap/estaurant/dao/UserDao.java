package net.therap.estaurant.dao;

import net.therap.estaurant.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Repository
public class UserDao extends AbstractDao<User> {

    public User findByEmail(String email) {

        try {
            return entityManager.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<User> findChef() {
        return entityManager.createNamedQuery("User.findChef", User.class).getResultList();
    }

    public List<User> findWaiter() {
        return entityManager.createNamedQuery("User.findWaiter", User.class).getResultList();
    }

    @Override
    public List<User> findAll() {
        return entityManager.createNamedQuery("User.findAll", User.class).getResultList();
    }

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void delete(int id) throws Exception {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User saveOrUpdate(User user) throws Exception {

        if (user.isNew()) {
            entityManager.persist(user);
            entityManager.flush();
        } else {
            user = entityManager.merge(user);
        }

        return user;
    }
}
