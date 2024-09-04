package com.example.cuentasandisbank.persistence;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.example.cuentasandisbank.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserPersistence {

        private static final String FILE_PATH = "src/main/resources/users.json";

        public List<User> getUsers() {
            try {
                return new ObjectMapper().readValue(new File(FILE_PATH), new TypeReference<List<User>>() {
                });
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public User getUserById(Integer id) {
            List<User> users = getUsers();
            if (users == null) {
                return null;
            }
            return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
        }

        public void saveUser(User user) {
            List<User> users = getUsers();
            if (users == null) {
                users = List.of(user);
            } else {
                users.add(user);
            }
            try {
                new ObjectMapper().writeValue(new File(FILE_PATH), users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void deleteUser(Integer id) {
            List<User> users = getUsers();
            if (users == null) {
                return;
            }
            users.removeIf(user -> user.getId().equals(id));
            try {
                new ObjectMapper().writeValue(new File(FILE_PATH), users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void updateUser(Integer id, User user) {
            List<User> users = getUsers();
            if (users == null) {
                return;
            }
            users.stream().filter(u -> u.getId().equals(id)).findFirst().ifPresent(u -> {
                u.setName(user.getName());
                u.setLastname(user.getLastname());
                u.setEmail(user.getEmail());
            });
            try {
                new ObjectMapper().writeValue(new File(FILE_PATH), users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
