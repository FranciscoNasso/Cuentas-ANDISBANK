package com.example.cuentasandisbank.controllers;

import com.example.cuentasandisbank.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService as;
    @GetMapping("")
    public ResponseEntity<?> getAccounts() {
        return ResponseEntity.ok(as.getAccounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Integer id) {
        return ResponseEntity.ok(as.getAccountById(id));
    }

    @PostMapping("")
    public ResponseEntity<?> createAccount() {
        return ResponseEntity.ok(as.createAccount())
    }
}
