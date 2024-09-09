package com.example.cuentasandisbank.persistence;
import com.example.cuentasandisbank.entities.Account;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountPersistence {
    private static final String FILE_PATH = "src/main/resources/Accounts.json";

    public List<Account> getAccount(){
        try {
            return new ObjectMapper().readValue(new File(FILE_PATH), new TypeReference<List<Account>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Account getAccountById(Integer id){
        List<Account> accounts = getAccount();
        if (accounts == null) {
            return null;
        }
        return accounts.stream().filter(account -> account.getId().equals(id)).findFirst().orElse(null);
    }

    public Account createAccount(Account account){
        List<Account> accounts = getAccount();
        if (accounts == null) {
            accounts = new ArrayList<>();
        }
        accounts.add(account);
        try {
            new ObjectMapper().writeValue(new File(FILE_PATH), accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return account;
    }

    public void deleteAccount(Integer id) {
        List<Account> accounts = getAccount();
        if (accounts == null) {
            return;
        }
        accounts.removeIf(account -> account.getId().equals(id));
        try {
            new ObjectMapper().writeValue(new File(FILE_PATH), accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
