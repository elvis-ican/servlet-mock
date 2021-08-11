package dev.elvis.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.elvis.daos.ActorDaoImpl;
import dev.elvis.models.Actor;
import dev.elvis.services.ActorService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class ActorServlet extends HttpServlet {

    private ActorService actorService = new ActorService(new ActorDaoImpl());

    public ActorServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp); this would just return a 405 status code

        // obtain list of actor objects from database or other source
        List<Actor> actors = actorService.getAll();

        // convert actor objects to json (Jackson databind)
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(actors);

        // use the printwriter to write json to response body
        try(PrintWriter pw = resp.getWriter()){
            pw.write(json);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // obtain json from request body
        try(BufferedReader reader = req.getReader();
            PrintWriter pw = resp.getWriter()){
            String actorJson = reader.readLine();

            // convert json to actor object
            ObjectMapper objectMapper = new ObjectMapper();
            Actor actor = objectMapper.readValue(actorJson, Actor.class);

            if(actor!=null && actor.getName().length()>100){
                resp.sendError(400, "Name too long");
                return;
            }

            // use service to add new actor
            Actor newlyCreatedActor = actorService.addNew(actor);

            // send some indicator of whether or not something was created successfully (200 if it went well,
            if(newlyCreatedActor!=null){
                resp.setStatus(201);
                pw.write(objectMapper.writeValueAsString(newlyCreatedActor));
            } else {
                resp.sendError(400);
            }
        }
    }
}
