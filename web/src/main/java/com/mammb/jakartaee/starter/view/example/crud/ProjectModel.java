package com.mammb.jakartaee.starter.view.example.crud;

import com.mammb.jakartaee.starter.app.example.crud.ProjectService;
import com.mammb.jakartaee.starter.domail.example.crud.Project;
import com.mammb.jakartaee.starter.domail.example.crud.ProjectState;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Named
@ViewScoped
public class ProjectModel implements Serializable {

    private static final Logger log = Logger.getLogger(ProjectModel.class.getName());

    @Inject
    private ProjectService service;

    @Inject
    private FacesContext facesContext;

    private String searchName;

    private List<Project> projects;

    private Project selected;


    @PostConstruct
    public void postConstruct() {
        log.info("#### @PostConstruct");
        this.searchName = "";
        this.projects = service.findAll("");
        this.selected = new Project();
    }

    public void search() {
        this.projects = service.findAll(this.searchName);
    }

    public void select(Project selected) {
        this.selected = selected;
    }

    public void save() {
        if (selected.isNew()) {
            service.save(selected);
        } else {
            service.update(selected);
        }

        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Saved!", "Save successful");
        facesContext.addMessage(null, m);
        this.selected = new Project();
        search();
    }

    public void delete(Project selected) {
        service.delete(selected);
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deleted!", "Delete successful");
        facesContext.addMessage(null, m);
        search();
    }

    public void preCreate() {
        this.selected = new Project();
    }

    public ProjectState[] getStatuses() {
        return ProjectState.values();
    }


    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public Project getSelected() {
        return selected;
    }

}
