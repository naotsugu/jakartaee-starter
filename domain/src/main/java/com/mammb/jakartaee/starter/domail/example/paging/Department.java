package com.mammb.jakartaee.starter.domail.example.paging;

import com.mammb.jakartaee.starter.lib.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;

import java.util.HashMap;
import java.util.Map;

@Entity(name = Department.NAME)
public class Department extends BasicEntity<Department> {

    public static final String NAME = "DEPARTMENTS";

    private String name;

    @OneToMany(mappedBy = "department")
    @MapKey(name = "name")
    private Map<String, Employee> employees;

    protected Department() {
        employees = Map.of();
    }

    public Department(String name, Map<String, Employee> employees) {
        this.name = name;
        this.employees = employees;
    }

    public static Department of(String name) {
        return new Department(name, new HashMap<>());
    }

    void add(Employee employee) {
        if (employees.containsKey(employee.getName())) {
            throw new RuntimeException("duplicate name");
        }
        employees.put(employee.getName(), employee);
    }

    public String getName() {
        return name;
    }

    public Map<String, Employee> getEmployees() {
        return employees;
    }
}
