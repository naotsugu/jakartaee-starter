package com.mammb.jakartaee.starter.view.example.crud;

import com.mammb.jakartaee.starter.app.example.crud.PlanService;
import com.mammb.jakartaee.starter.domail.example.crud.Plan;
import com.mammb.jakartaee.starter.domail.example.crud.PlanState;
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
public class PlanModel implements Serializable {

    private static final Logger log = Logger.getLogger(PlanModel.class.getName());

    @Inject
    private PlanService service;

    @Inject
    private FacesContext facesContext;

    private String searchName;

    private List<Plan> plans;

    private Plan selected;


    @PostConstruct
    public void postConstruct() {
        log.info("#### @PostConstruct");
        this.searchName = "";
        this.plans = service.findAll("");
        this.selected = new Plan();
    }

    public void search() {
        this.plans = service.findAll(this.searchName);
    }

    public void select(Plan selected) {
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
        this.selected = new Plan();
        search();
    }

    public void delete(Plan selected) {
        service.delete(selected);
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deleted!", "Delete successful");
        facesContext.addMessage(null, m);
        search();
    }

    public void preCreate() {
        this.selected = new Plan();
    }

    public PlanState[] getStatuses() {
        return PlanState.values();
    }


    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public List<Plan> getPlans() {
        return plans;
    }

    public Plan getSelected() {
        return selected;
    }

}
