package com.example.cuentasandisbank.controllers;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.http.HttpStatus;
import com.example.cuentasandisbank.entities.User;
import com.example.cuentasandisbank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;

@RestController
@RequestMapping("/user")
public class UserController {


    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(10, Refill.intervally(1, Duration.ofSeconds(1))))
            .build();

    private final Semaphore semaphore = new Semaphore(5);

    @Autowired
    private UserService UserService;

    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        int tokensToConsume = 1;
        if (bucket.tryConsume(tokensToConsume)) {
            List<User> users = UserService.getUsers();
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests");
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            if (semaphore.tryAcquire()) {
                try {
                    User user = UserService.getUserById(id);
                    return ResponseEntity.ok(user);
                } finally {
                    semaphore.release();
                }
            } else {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        UserService.saveUser(user);
        return ResponseEntity.ok("User saved");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        UserService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user) {
        UserService.updateUser(id, user);
        return ResponseEntity.ok("User updated");
    }
}
