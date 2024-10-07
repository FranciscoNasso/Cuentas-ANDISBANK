package com.example.cuentasandisbank.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final Bucket bucket = Bucket.builder()
            .addLimit(Bandwidth.classic(10, Refill.intervally(1, Duration.ofSeconds(65))))
            .build();

    private final Semaphore semaphore = new Semaphore(1);

    @Autowired
    private UserService UserService;

    @GetMapping("")
    public ResponseEntity<?> getUsers() {
        int tokensToConsume = 1;
        logger.info("Getting user endpoint hit");
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
            logger.info("Getting user by id endpoint hit");
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
        logger.info("Saving user endpoint hit");
        UserService.saveUser(user);
        return ResponseEntity.ok("User saved");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        logger.info("Deleting user endpoint hit");
        UserService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user) {
        logger.info("Updating user endpoint hit");
        UserService.updateUser(id, user);
        return ResponseEntity.ok("User updated");
    }
}
