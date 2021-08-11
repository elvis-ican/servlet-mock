package dev.elvis.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    private static Connection connection;
    private static final boolean IS_TEST = Boolean.parseBoolean(System.getenv("TEST"));

    public static Connection getConnection() throws SQLException {

        if(connection==null || connection.isClosed()){
            if(IS_TEST){
                // connect to H2
                connection = DriverManager.getConnection("jdbc:h2:~/test");
            } else {
                /*
                jdbc connection string format: jdbc:postgresql://host:port/database
                optionally if we're accessing a different schema: jdbc:postgresql://host:port/database?currentSchema=schemaName
                 */
                try {
                    Class.forName("org.postgresql.Driver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                String url = "jdbc:postgresql://training-db.cdoaisum0qfz.us-east-2.rds.amazonaws" +
                        ".com:5432/postgres";
                final String PASSWORD = System.getenv("PASSWORD");
                final String USERNAME = System.getenv("USERNAME");
                connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
            }
        }
        //        System.out.println(connection.getMetaData().getDriverName());
        return connection;
    }
}
