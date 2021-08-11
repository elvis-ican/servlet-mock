package dev.elvis.daos;

import dev.elvis.models.Actor;

import java.util.List;

public interface ActorDao {

    public List<Actor> getAllActors();
    public Actor addNewActor(Actor newActor);

}
