package com.mammb.jakartaee.starter.domail.example.ejb;

import com.mammb.jakartaee.starter.domail.BaseEntity;
import com.mammb.jakartaee.starter.domail.repository.SortSpec;
import com.mammb.jakartaee.starter.domail.repository.Specification;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@MappedSuperclass
public class Person extends BaseEntity<Person> {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    protected Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
