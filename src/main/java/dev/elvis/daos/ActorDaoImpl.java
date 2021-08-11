package dev.elvis.daos;

import dev.elvis.models.Actor;
import dev.elvis.util.ConnectionUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ActorDaoImpl implements ActorDao {

    @Override
    public List<Actor> getAllActors() {
        String sql = "select id, actor_name, birthdate from actor";
        try (Connection connection = ConnectionUtil.getConnection();
             Statement s = connection.createStatement();
             ResultSet rs = s.executeQuery(sql);) {
            List<Actor> allActors = new ArrayList<>();
            while(rs.next()){
                String actorName = rs.getString("actor_name");
                Actor actor = new Actor();
                actor.setName(actorName);
                actor.setId(rs.getInt("id"));
                actor.setBirthday(rs.getObject("birthdate", LocalDate.class));
                allActors.add(actor);
            }
            return allActors;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Actor addNewActor(Actor newActor) {
        String sql = " {call add_actor(?, ?) } ";

        try(Connection connection = ConnectionUtil.getConnection();
            CallableStatement callableStatement = connection.prepareCall(sql)){
            callableStatement.setString(1, newActor.getName());
            callableStatement.setObject(2, newActor.getBirthday());

            callableStatement.execute();

            ResultSet resultSet = callableStatement.getResultSet();

            if(resultSet.next()){
                newActor.setId(resultSet.getInt("id"));
                return newActor;
            }

        } catch (SQLException throwables) {
            return null;
        }
        return null;
    }



    public boolean addNewActorPreparedStatement(Actor newActor) {
        String sql = "insert into actor values (default, ?,?)";
        try (Connection connection = ConnectionUtil.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setString(1,newActor.getName());
            ps.setObject(2,newActor.getBirthday());
            int success = ps.executeUpdate();
            if(success>0){
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
