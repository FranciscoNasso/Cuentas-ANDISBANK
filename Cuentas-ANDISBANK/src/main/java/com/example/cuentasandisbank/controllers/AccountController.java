package com.example.cuentasandisbank.controllers;

import com.example.cuentasandisbank.entities.Account;
import com.example.cuentasandisbank.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
        Account a = as.createAccount(account);
        if (a == null) {
            return ResponseEntity.badRequest().body("Account already exists");
        }else {
            return ResponseEntity.ok(a);
            }
        }
}
