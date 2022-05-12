package com.mammb.jakartaee.starter.domail.example.criteria;

import com.mammb.jakartaee.starter.lib.entity.BasicEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;

@Entity
public class Project extends BasicEntity<Project> {

    private String name;

    @Enumerated(EnumType.STRING)
    private ProjectState state;

    @Embedded
    private Duration duration;

    @ManyToOne
    private Project parent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProjectState getState() {
        return state;
    }

    public void setState(ProjectState state) {
        this.state = state;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Project getParent() {
        return parent;
    }

    public void setParent(Project parent) {
        this.parent = parent;
    }
}

