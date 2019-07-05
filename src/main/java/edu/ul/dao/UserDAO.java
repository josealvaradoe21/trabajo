/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ul.dao;
import edu.ul.usermg.User;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ulima
 */


public class UserDAO {
    

    private static final String INSERT_USERS_SQL = "INSERT INTO public.users" + "  (name, email, country) VALUES " +
        " (?, ?, ?);";

    private static final String SELECT_USER_BY_ID = "select id,name,email,country from public.users  where id =?";
    private static final String SELECT_ALL_USERS = "select * from public.users ";
    private static final String DELETE_USERS_SQL = "delete from public.users where id = ?;";
    private static final String UPDATE_USERS_SQL = "update public.users set name = ?,email= ?, country =? where id = ?;";

    //private final String url = "postgres://guzkmkbgolrhyx:eac0beb148b3bab8c06643e864f10dbad16483f56a9c6ae2405d40f8ca006f83@ec2-50-19-221-38.compute-1.amazonaws.com:5432/d16k4ba5r9hk39";
    //private final String user = "guzkmkbgolrhyx";
    //private final String password = "eac0beb148b3bab8c06643e864f10dbad16483f56a9c6ae2405d40f8ca006f83";
    public UserDAO() {}

protected Connection getConnection() throws URISyntaxException {
        Connection conn = null;
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String user = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
        + dbUri.getPort() + dbUri.getPath()
        + "?sslmode=require";
        
        try {
            conn = DriverManager.getConnection(dbUrl, user, password);
           
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
 
        return conn;
    }
  

    public void insertUser(User user) throws SQLException, URISyntaxException {
        System.out.println(INSERT_USERS_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCountry());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public User selectUser(int id) throws SQLException, URISyntaxException {
        User user = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                user = new User(id, name, email, country);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return user;
    }

    public List < User > selectAllUsers() throws SQLException, URISyntaxException {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List < User > users = new ArrayList < > ();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                users.add(new User(id, name, email, country));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return users;
    }

    public boolean deleteUser(int id) throws SQLException, URISyntaxException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(User user) throws SQLException, URISyntaxException {
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getCountry());
            statement.setInt(4, user.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
