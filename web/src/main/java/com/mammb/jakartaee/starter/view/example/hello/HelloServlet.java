package com.mammb.jakartaee.starter.view.example.hello;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "helloServlet", urlPatterns = { "/helloServlet" })
public class HelloServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<html><head><title>HelloServlet</title></head>");
        writer.println("<body><h3>Hello Servlet</h3></body>");
        writer.println("</html>");
    }
}
