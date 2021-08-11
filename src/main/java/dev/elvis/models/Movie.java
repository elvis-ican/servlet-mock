package dev.elvis.models;

import java.util.List;
import java.util.Objects;

public class Movie {

    private int id;
    private String name;
    private int releaseYear;
    private int duration;
    private List<Actor> actorList;

    public Movie(){
        super();
    }

    public Movie(List<Actor> actorList){
        super();
        this.actorList = actorList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return id == movie.id && releaseYear == movie.releaseYear && duration == movie.duration && Objects.equals(name, movie.name) && Objects.equals(actorList, movie.actorList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, releaseYear, duration, actorList);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                ", duration=" + duration +
                ", actorList=" + actorList +
                '}';
    }
}
