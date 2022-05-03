package com.mammb.jakartaee.starter.domail.example.paging;

import com.mammb.jakartaee.starter.lib.entity.BasicEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity(name = Employee.NAME)
public class Employee extends BasicEntity<Employee> {

    public static final String NAME = "EMPLOYEES";

    private String name;

    @ManyToOne
    private Department department;


    protected Employee() {
    }

    public Employee(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    public static Employee of(String name) {
        return new Employee(name, null);
    }

    public Employee belong(Department department) {
        this.department = department;
        this.department.add(this);
        return this;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }

}
