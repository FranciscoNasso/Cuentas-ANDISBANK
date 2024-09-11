package com.example.cuentasandisbank.services;

import com.example.cuentasandisbank.entities.User;
import com.example.cuentasandisbank.persistence.UserPersistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserPersistence UserPersistence;

    public List<User> getUsers() {
        return UserPersistence.getUsers();
    }

    public User getUserById(String id) {
        return UserPersistence.getUserById(id);
    }

    public void saveUser(User user) {
        UserPersistence.saveUser(user);
    }

    public void deleteUser(String id) {
        UserPersistence.deleteUser(id);
    }

    public void updateUser(String id, User user) {
        UserPersistence.updateUser(id, user);
    }

}
