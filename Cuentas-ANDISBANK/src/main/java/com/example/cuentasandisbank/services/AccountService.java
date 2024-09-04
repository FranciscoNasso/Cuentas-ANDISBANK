package com.example.cuentasandisbank.services;
import com.example.cuentasandisbank.entities.Account;
import com.example.cuentasandisbank.persistence.AccountPersistence;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountPersistence ap;
    public List<Account> getAccounts() {
        return ap.getAccount();
    }
}
