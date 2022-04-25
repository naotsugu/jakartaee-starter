package com.mammb.jakartaee.starter.app.example.ejb;

import com.mammb.jakartaee.starter.domail.example.ejb.Customer;
import jakarta.ejb.Local;

import java.util.List;

@Local
public interface CustomerService {

    Long save(Customer customer);

    void update(Customer customer);

    List<Customer> findAll();

}
