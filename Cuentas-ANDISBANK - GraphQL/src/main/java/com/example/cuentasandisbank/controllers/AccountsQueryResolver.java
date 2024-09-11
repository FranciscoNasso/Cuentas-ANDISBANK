package com.example.cuentasandisbank.controllers;

import com.example.cuentasandisbank.entities.Account;
import com.example.cuentasandisbank.services.AccountService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountsQueryResolver implements GraphQLQueryResolver {

    @Autowired
    AccountService accountService;

    public AccountsQueryResolver(AccountService accountService) {
        this.accountService = accountService;
    }

    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }
}
