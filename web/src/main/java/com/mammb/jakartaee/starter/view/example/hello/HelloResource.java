package com.mammb.jakartaee.starter.view.example.hello;

import com.mammb.jakartaee.starter.app.example.hello.HelloCdiService;
import com.mammb.jakartaee.starter.app.example.hello.HelloEjbService;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("hello")
public class HelloResource {

    @EJB
    private HelloEjbService helloEjbService;

    @Inject
    private HelloCdiService helloCdiService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Greeting get() {
        return new Greeting("Hello", "JAX-RS");
    }

    public static class Greeting {
        private String message;
        private String name;
        public Greeting(String message, String name) {
            this.message = message;
            this.name = name;
        }
        public String getMessage() {
            return message;
        }
        public String getName() {
            return name;
        }
    }

    @GET
    @Path("ejb")
    @Produces(MediaType.TEXT_PLAIN)
    public String getEjbHello() {
        return helloEjbService.hello();
    }


    @GET
    @Path("cdi")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCdiHello() {
        return helloCdiService.hello();
    }

}
