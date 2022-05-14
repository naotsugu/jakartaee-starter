package com.mammb.jakartaee.starter.view.example.h2;

import jakarta.servlet.annotation.WebServlet;
import org.h2.server.web.JakartaWebServlet;

@WebServlet(name = "h2console", urlPatterns = { "/h2console/*" })
public class H2Console extends JakartaWebServlet {
}

