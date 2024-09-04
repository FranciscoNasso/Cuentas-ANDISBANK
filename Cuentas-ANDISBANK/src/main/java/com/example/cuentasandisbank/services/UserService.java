package com.example.cuentasandisbank.services;

import com.example.cuentasandisbank.entities.User;
import com.example.cuentasandisbank.persistence.UserPersistence;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public List<User> getUsers() {
        return UserPersistence.getUsers();
    }

    public User getUserById(Integer id) {
        return UserPersistence.getUserById(id);
    }

    public void saveUser(User user) {
        UserPersistence.saveUser(user);
    }

    public void deleteUser(Integer id) {
        UserPersistence.deleteUser(id);
    }

    public void updateUser(Integer id, User user) {
        UserPersistence.updateUser(id, user);
    }

}
