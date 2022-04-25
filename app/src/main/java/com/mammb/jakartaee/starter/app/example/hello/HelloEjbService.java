package com.mammb.jakartaee.starter.app.example.hello;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

@LocalBean
@Stateless
public class HelloEjbService {
    public String hello() {
        return "Hello EJB";
    }
}
