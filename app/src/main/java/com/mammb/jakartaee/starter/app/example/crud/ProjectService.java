package com.mammb.jakartaee.starter.app.example.crud;

import com.mammb.jakartaee.starter.domail.example.crud.Project;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
@RequestScoped
public class ProjectService {

    @Inject
    private ProjectRepository repository;

    public List<Project> findAll(String name) {
        return repository.findByNameLike(name);
    }

    public Long save(Project project) {
        repository.save(project);
        return project.getId();
    }

    public void update(Project project) {
        repository.save(project);
    }

    public void delete(Project project) {
        repository.delete(project);
    }

}
