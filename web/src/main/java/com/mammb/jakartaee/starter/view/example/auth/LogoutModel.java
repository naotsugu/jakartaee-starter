package com.mammb.jakartaee.starter.view.example.auth;

import jakarta.enterprise.inject.Model;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import java.util.logging.Logger;

@Model
public class LogoutModel {

    private static final Logger log = Logger.getLogger(LogoutModel.class.getName());

    @Inject
    private ExternalContext externalContext;

    public String logout() {
        log.info("#### logout:" + externalContext.getUserPrincipal().getName());
        externalContext.invalidateSession();
        return "/example/auth/login.xhtml?faces-redirect=true";
    }

}
