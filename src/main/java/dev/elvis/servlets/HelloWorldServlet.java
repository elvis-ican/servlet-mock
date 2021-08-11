package dev.elvis.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HelloWorldServlet extends HttpServlet {

    public HelloWorldServlet(){};

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("Hello world, request made to hello world servlet");
        try(PrintWriter pw = response.getWriter();){
            pw.write("hello world from our first servlet");
        }
    }


}
