package com.mammb.jakartaee.starter.domail.example.crud;

import com.mammb.jakartaee.starter.domail.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDate;

@Entity(name = Plan.NAME)
public class Plan extends BaseEntity<Plan> {

    public static final String NAME = "PROJECTS";

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PlanState state;

    @NotEmpty
    private String name;

    private LocalDate openDate;

    private LocalDate closeDate;

    private String note;


    public Plan() {

    }

    public Plan(PlanState state, String name, LocalDate openDate, LocalDate closeDate, String note) {
        this.state = state;
        this.name = name;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.note = note;
    }

    public PlanState getState() {
        return state;
    }

    public void setState(PlanState state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

