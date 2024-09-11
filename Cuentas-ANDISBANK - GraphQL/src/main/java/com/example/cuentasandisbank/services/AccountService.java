package com.example.cuentasandisbank.services;
import com.example.cuentasandisbank.entities.Account;
import com.example.cuentasandisbank.persistence.AccountPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    @Autowired
    private AccountPersistence ap;

    public List<Account> getAccounts() {
        return ap.getAccount();
    }

    public Account getAccountById(Integer id) {
        return ap.getAccountById(id);
    }

    public Account createAccount(Account account) {
        List<Account> accounts = ap.getAccount();
        if (accounts.contains(account)) {
            return null;
        }
        return ap.createAccount(account);
    }

    public void deleteAccount(Integer id) {
        ap.deleteAccount(id);
    }
}
