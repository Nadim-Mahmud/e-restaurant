package net.therap.estaurant.service;

import net.therap.estaurant.dao.UserDao;
import net.therap.estaurant.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author nadimmahmud
 * @since 1/17/23
 */
@Service
public class UserService {

    @Autowired
    public UserDao userDao;

    public List<User> findChef() {
        return userDao.findChef();
    }

    public List<User> findWaiter() {
        return userDao.findWaiter();
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(int id) {
        return userDao.findById(id);
    }

    @Transactional
    public void delete(int id) throws Exception {
        userDao.delete(id);
    }

    @Transactional
    public User saveOrUpdate(User user) throws Exception {
        return userDao.saveOrUpdate(user);
    }

    public boolean isValidCredential(User user) {
        User savedUser = userDao.findByEmail(user.getEmail()).get(0);

        return user.equals(savedUser);
    }

    public boolean isDuplicateEmail(User user) {

        List<User> userList = userDao.findByEmail(user.getEmail());

        if (userList.size() == 0) {
            return false;
        }

        return userList.get(0).getId() != user.getId();
    }
}
