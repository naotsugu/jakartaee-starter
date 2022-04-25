package com.mammb.jakartaee.starter.domail.example.ejb;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;

@Entity(name = Customer.NAME)
public class Customer extends Person {

    public static final String NAME = "CUSTOMERS";

    @NotEmpty
    private String address;

    @NotEmpty
    private String city;

    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String telephone;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String address, String city, String telephone) {
        super(firstName, lastName);
        this.address = address;
        this.city = city;
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
