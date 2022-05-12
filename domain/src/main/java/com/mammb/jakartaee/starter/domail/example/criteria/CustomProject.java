package com.mammb.jakartaee.starter.domail.example.criteria;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import java.util.Set;

@Entity
public class CustomProject extends Project {

    @ElementCollection
    private Set<String> labels;

}
