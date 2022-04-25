package com.mammb.jakartaee.starter.app.example.auth;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Transactional
@RequestScoped
public class UserService {

    private final UserRepository repository;

    @Inject
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

}
