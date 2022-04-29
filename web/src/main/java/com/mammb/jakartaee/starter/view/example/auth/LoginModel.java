package com.mammb.jakartaee.starter.view.example.auth;

import jakarta.enterprise.inject.Model;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.util.logging.Logger;

@Model
public class LoginModel {

    private static final Logger log = Logger.getLogger(LoginModel.class.getName());

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Inject
    private SecurityContext securityContext;
    @Inject
    private FacesContext facesContext;
    @Inject
    private ExternalContext externalContext;


    public void login() throws Exception {

        Credential credential =
            new UsernamePasswordCredential(username, new Password(password));

        externalContext.invalidateSession();

        AuthenticationStatus status = securityContext.authenticate(
            (HttpServletRequest) externalContext.getRequest(),
            (HttpServletResponse) externalContext.getResponse(),
            AuthenticationParameters.withParams()
                .newAuthentication(true)
                .credential(credential));

        log.info("#### AuthenticationStatus:" + status);

        switch (status) {
            case SEND_CONTINUE, NOT_DONE -> facesContext.responseComplete();
            case SEND_FAILURE -> facesContext.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Authentication failed", null));
            case SUCCESS -> {
                externalContext.redirect(externalContext.getRequestContextPath() + "/example/auth/private/home.xhtml");
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
