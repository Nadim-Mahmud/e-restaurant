package net.therap.estaurant.service;

import net.therap.estaurant.command.Credentials;
import net.therap.estaurant.command.Profile;
import net.therap.estaurant.dao.UserDao;
import net.therap.estaurant.entity.AccessStatus;
import net.therap.estaurant.entity.User;
import net.therap.estaurant.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Objects;

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
        User user = userDao.findById(id);

        if(Objects.isNull(user)){
            throw new RuntimeException();
        }

        return user;
    }

    public User findByEmail(String email) throws Exception {
        List<User> userList = userDao.findByEmail(email);

        if(userList.size() == 0){
            throw new RuntimeException();
        }

        return userList.get(0);
    }

    @Transactional
    public void delete(int id) throws Exception {
        User user = userDao.findById(id);
        user.setAccessStatus(AccessStatus.DELETED);
        userDao.saveOrUpdate(user);
    }

    @Transactional
    public User saveOrUpdate(User user) throws Exception {
        return userDao.saveOrUpdate(user);
    }

    public boolean isValidCredential(Credentials credentials) throws NoSuchAlgorithmException, InvalidKeySpecException {
        List<User> userList = userDao.findByEmail(credentials.getEmail());

        if (userList.size() == 0) {
            return false;
        }

        return userList.get(0).getPassword().equals(Encryption.getPBKDF2(credentials.getPassword()));
    }

    public boolean isDuplicateEmail(User user) {

        List<User> userList = userDao.findByEmail(user.getEmail());

        if (userList.size() == 0) {
            return false;
        }

        return userList.get(0).getId() != user.getId();
    }

    public Profile getProfileObject(User user){
        return new Profile(user.getId(), user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getEmail());
    }

    public User updateUserByProfile(User user, Profile profile){
        user.setId(profile.getId());
        user.setFirstName(profile.getFirstName());
        user.setLastName(profile.getLastName());
        user.setDateOfBirth(profile.getDateOfBirth());
        user.setEmail(profile.getEmail());

        return user;
    }
}
