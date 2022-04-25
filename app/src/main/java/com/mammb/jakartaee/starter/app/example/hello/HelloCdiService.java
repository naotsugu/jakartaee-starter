package com.mammb.jakartaee.starter.app.example.hello;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HelloCdiService {
    public String hello() {
        return "Hello CDI";
    }
}
