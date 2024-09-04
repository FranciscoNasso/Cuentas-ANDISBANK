package com.example.cuentasandisbank.controllers;

import com.example.cuentasandisbank.entities.User;

import com.example.cuentasandisbank.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {


    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        List<User> users = UserService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        Optional<User> user = UserService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        UserService.saveUser(user);
        return ResponseEntity.ok("User saved");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        UserService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user) {
        UserService.updateUser(id, user);
        return ResponseEntity.ok("User updated");
    }
}
