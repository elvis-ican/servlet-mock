package dev.elvis.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.Objects;

public class Actor {

    private int id;
    private String name;
    @JsonIgnore
    private LocalDate birthday;

    public Actor(){
//        super();
    }

    public Actor(int id){
        super();
        this.id = id;
    }

    public Actor(String name){
        super();
        this.name = name;
    }

    public Actor(String name, LocalDate birthday){
        super();
        this.name = name;
        this.birthday = birthday;
    }

    public Actor(int id, String name){
        super();
        this.id = id;
        this.name = name;
    }

    public Actor(int id, String name, LocalDate birthday){
        super();
        this.id = id;
        this.name = name;
        this.birthday = birthday;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return id == actor.id && Objects.equals(name, actor.name) && Objects.equals(birthday, actor.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthday);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
