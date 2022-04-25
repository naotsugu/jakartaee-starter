package com.mammb.jakartaee.starter.view.example.ejb;

import com.mammb.jakartaee.starter.app.example.ejb.CustomerService;
import com.mammb.jakartaee.starter.domail.example.ejb.Customer;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
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
public class CustomerModel implements Serializable {

    private static final Logger log = Logger.getLogger(CustomerModel.class.getName());

    @EJB
    private CustomerService service;

    @Inject
    private FacesContext facesContext;

    private List<Customer> customers;

    private Customer selected;


    @PostConstruct
    public void postConstruct() {
        log.info("#### @PostConstruct");
        this.customers = service.findAll();
        this.selected = new Customer();
    }

    public void select(Customer selected) {
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
        this.selected = new Customer();
        this.customers = service.findAll();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public Customer getSelected() {
        return selected;
    }

}
