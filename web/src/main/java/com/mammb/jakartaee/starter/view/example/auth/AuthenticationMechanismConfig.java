package com.mammb.jakartaee.starter.view.example.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;

@CustomFormAuthenticationMechanismDefinition(
    loginToContinue = @LoginToContinue(
        loginPage="/example/auth/login.xhtml",
        errorPage="/example/auth/login.xhtml",
        useForwardToLogin = false
    )
)
@ApplicationScoped
public class AuthenticationMechanismConfig {
}
