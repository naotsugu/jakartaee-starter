package com.mammb.jakartaee.starter.view.example.entitymeta;

import com.mammb.jakartaee.starter.app.example.entitymeta.EntityMetaService;
import com.mammb.jakartaee.starter.app.example.entitymeta.Table;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

@Path("entitymetamodel")
public class EntityMetaResource implements Serializable {

    private static final Logger log = Logger.getLogger(EntityMetaResource.class.getName());

    @Inject
    private EntityMetaService service;

    @PostConstruct
    public void postConstruct() {
        log.info("#### @PostConstruct");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Table> get() {
        return service.tables();
    }

}

