package com.mammb.jakartaee.starter.view.example.cdievent;

import com.mammb.jakartaee.starter.app.example.cdievent.CdiEventService;
import com.mammb.jakartaee.starter.domail.example.cdievent.CdiEventLog;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Model
public class CdiEventModel implements Serializable {

    private static final Logger log = Logger.getLogger(CdiEventModel.class.getName());

    @Inject
    private CdiEventService service;

    private List<CdiEventLog> logs;

    private String message;

    @PostConstruct
    public void postConstruct() {
        log.info("#### @PostConstruct");
        this.message = "";
        this.logs = service.findAll();
    }

    public void deleteAll() {
        service.deleteAll();
        logs = service.findAll();
    }

    public void fire() {
        service.fireEvent(message);
        logs = service.findAll();
    }

    public List<CdiEventLog> getLogs() {
        return logs;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
