package dev.elvis.daos;

import dev.elvis.models.Actor;
import dev.elvis.models.Movie;
import dev.elvis.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;

public class MovieDaoImpl implements MovieDao{

    public List<Movie> getAllMoviesFirstAttempt() {
        String sql = "select m.id, movie_name, release_year, duration, actor_id, actor_name, birthdate" +
                " from {oj movie m left join movie_actor ma on m.id = ma.movie_id " +
                "left join actor a on a.id = ma.actor_id }";
        try(Connection connection = ConnectionUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)){

            /*
            int numOfColumns = rs.getMetaData().getColumnCount();
            for(int i=1; i<numOfColumns+1; i++){
                String columnName = rs.getMetaData().getColumnName(i);
                System.out.println(columnName);
            }
             */
            Set<Movie> movieSet = new HashSet<>();
            List<Actor> newActorList = new LinkedList<>();
            while(rs.next()){
                Movie m = new Movie();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("movie_name"));
                m.setReleaseYear(rs.getInt("release_year"));
                m.setDuration(rs.getInt("duration"));
                boolean isNewMovie = movieSet.add(m);
                if(isNewMovie){
                    newActorList = new LinkedList<>();
                    int id = rs.getInt("actor_id");
                    String name = rs.getString("actor_name");
                    LocalDate date = rs.getObject("birthdate", LocalDate.class);
                    Actor newActor = new Actor(id, name, date);
                    m.setActorList(newActorList);
                } else {

                }
            }

            return new ArrayList<>(movieSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean addNewMovie(Movie newMovie) {
        return false;
    }

    @Override
    public List<Movie> getAllMovies() {

        // sql string makes two joins and is ordered by movie id so that records with the same movie are consecutive
        String sql = "select m.id, movie_name, release_year, duration, actor_id, actor_name, birthdate" +
                " from {oj movie m left join movie_actor ma on m.id = ma.movie_id " +
                "left join actor a on a.id = ma.actor_id } order by m.id";
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            List<Movie> movies = new ArrayList<>();
            // currentMovie keeps track of the movies as we go through them, represents the previous movie when the
            // while loop executes again
            Movie currentMovie = null;
            while (rs.next()) {
                int newId = rs.getInt("id");

                // if the new id from the result set does not have the same id as the previous movie, we need to
                // create a new movie object and add it to the list of movies
                if (currentMovie == null || newId != currentMovie.getId()) {
                    //create new movie
                    currentMovie = new Movie();
                    currentMovie.setId(newId);
                    currentMovie.setName(rs.getString("movie_name"));
                    currentMovie.setReleaseYear(rs.getInt("release_year"));
                    currentMovie.setDuration(rs.getInt("duration"));
                    movies.add(currentMovie);
                }

                // whether or not the movie has been added in this iteration, we'll associate the current actor with
                // the most recent movie, if it does in fact have an actor associated with it
                int actorId = rs.getInt("actor_id");
                if (actorId != 0) {
                    String name = rs.getString("actor_name");
                    LocalDate date = rs.getObject("birthdate", LocalDate.class);
                    Actor newActor = new Actor(actorId, name, date);
                    if (currentMovie.getActorList() == null) {
                        currentMovie.setActorList(new ArrayList<>());
                    }
                    currentMovie.getActorList().add(newActor);
                }

            }
            return movies;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public List<Movie> getAllMoviesMapSolution(){
        String sql = "select m.id, movie_name, release_year, duration, actor_id, actor_name, birthdate" +
                " from {oj movie m left join movie_actor ma on m.id = ma.movie_id " +
                "left join actor a on a.id = ma.actor_id } order by m.id";
        try (Connection connection = ConnectionUtil.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            // use a map to store the movieName:Movie pairs
            Map<String, Movie> movieMap = new HashMap<>();
            while(rs.next()) {
                Movie m = new Movie();
                m.setName(rs.getString("movie_name"));
                String actorName = rs.getString("actor_name");
                String movieName = m.getName();
                if (!movieMap.containsKey(movieName)) {
                    m.setId(rs.getInt("id"));
                    m.setDuration(rs.getInt("duration"));
                    m.setReleaseYear(rs.getInt("release_year"));
                    //set all other wanted data...
                    if (actorName != null) {
                        List<Actor> actors = new ArrayList<>();
                        int actorId = rs.getInt("actor_id");
                        actors.add(new Actor(actorId, actorName));
                        m.setActorList(actors);
                    }
                    movieMap.put(movieName, m);
                } else if (actorName != null) {
                    int actorId = rs.getInt("actor_id");
                    movieMap.get(movieName).getActorList().add(new Actor(actorId, actorName));
                }
            }
            return new ArrayList<>(movieMap.values());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
