package com.example.cuentasandisbank.controllers;

import com.example.cuentasandisbank.entities.Account;
import com.example.cuentasandisbank.services.AccountService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.github.bucket4j.Bucket;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService as;
    private final Bucket fixedWindowBucket = Bucket.builder()
            .addLimit(Bandwidth.classic(10, Refill.greedy(10, java.time.Duration.ofMinutes(1)))).build();
    private final Bucket slidingWindowBucket = Bucket.builder()
            .addLimit(Bandwidth.simple(10, java.time.Duration.ofMinutes(1))).build();

    // implementacion con Fixed Window Rate Limiter
    @GetMapping("")
    public ResponseEntity<?> getAccounts() {
        int tokens = 1;
        if (fixedWindowBucket.tryConsume(tokens)) {
            return ResponseEntity.ok(as.getAccounts());
        } else {
            return ResponseEntity.status(429).body("Too many requests");
        }
    }

    // implementacion con Sliding Window Rate Limiter
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Integer id) {
        int tokens = 1;
        if (slidingWindowBucket.tryConsume(tokens)) {
            if (as.getAccountById(id) == null) {
                return ResponseEntity.badRequest().body("Account not found");
            }
            return ResponseEntity.ok(as.getAccountById(id));
        } else {
            return ResponseEntity.status(429).body("Too many requests");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        Account a = as.createAccount(account);
        if (a == null) {
            return ResponseEntity.badRequest().body("Account already exists");
        }else {
            return ResponseEntity.ok(a);
            }
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Integer id) {
        as.deleteAccount(id);
        return ResponseEntity.ok("Account deleted");
    }
}
