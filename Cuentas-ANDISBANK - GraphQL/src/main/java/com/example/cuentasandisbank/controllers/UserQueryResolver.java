package com.example.cuentasandisbank.controllers;

import com.example.cuentasandisbank.entities.User;
import com.example.cuentasandisbank.services.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserQueryResolver implements GraphQLQueryResolver {

    @Autowired
    UserService userService;

    public UserQueryResolver(UserService userService) {
        this.userService = userService;
    }

    public List<User> getUsers() {
        return userService.getUsers();
    }


}
