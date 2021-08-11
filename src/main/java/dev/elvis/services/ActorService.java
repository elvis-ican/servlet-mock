package dev.elvis.services;

import dev.elvis.daos.ActorDao;
import dev.elvis.models.Actor;

import java.util.List;

public class ActorService {

    private ActorDao actorDao;

    public ActorService(ActorDao actorDao){
        this.actorDao = actorDao;
    }

    public List<Actor> getAll() {
        return actorDao.getAllActors();
    }

    public Actor addNew(Actor actor){
        return actorDao.addNewActor(actor);
    }

}
