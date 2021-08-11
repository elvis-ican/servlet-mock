package dev.elvis.daos;

import dev.elvis.models.Movie;

import java.util.List;

public interface MovieDao {

    public List<Movie> getAllMovies();
    public boolean addNewMovie(Movie newMovie);

}
