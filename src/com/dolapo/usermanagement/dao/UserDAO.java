package com.dolapo.usermanagement.dao;

import com.dolapo.usermanagement.model.User;
import com.dolapo.usermanagement.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

  //define sql statements

  private static final String INSERT_USERS_SQL = "INSERT INTO users" + " (name, email, country) " +
      "VALUES " + "(?, ?, ?);";

  private static final String SELECT_USER_BY_ID = "select id, name, email, country from users where id = ?";
  private static final String SELECT_ALL_USERS = "select * from users";
  private static final String DELETE_USER_SQL = "delete from users where id = ?;";
  private static final String UPDATE_USER_SQL = "update users set name = ?, email=?, country=? where id = ?;";

  //insert record in database
  public void insertUser(User user) {
    try (Connection connection = JDBCUtils.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
      preparedStatement.setString(1, user.getName());
      preparedStatement.setString(2, user.getEmail());
      preparedStatement.setString(3, user.getCountry());
      preparedStatement.executeUpdate();
    } catch (Exception e) {
      System.err.println(e.getLocalizedMessage());
    }
  }

  public User selectUser(int id) {
    User user = null;
    try (Connection connection = JDBCUtils.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String country = resultSet.getString("country");

        user = new User(name, email, country);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return user;
  }

  // select all users from database
  public List<User> selectAllUsers() {
    List<User> users = new ArrayList<>();
    try (Connection connection = JDBCUtils.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
      System.out.println(preparedStatement);
      ResultSet rs = preparedStatement.executeQuery();
      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String country = rs.getString("country");
        users.add(new User(id, name, email, country));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return users;
  }

  //delete user from database
  public boolean deleteUser(int id) {
    boolean rowDeleted = false;
    try (Connection connection = JDBCUtils.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_SQL)) {
      preparedStatement.setInt(1, id);
      rowDeleted = preparedStatement.executeUpdate() > 0;
    }catch (SQLException e) {
      e.printStackTrace();
    }
    return rowDeleted;
  }

  public boolean updateUser(User user) {
    boolean rowUpdated = false;
    try(Connection connection = JDBCUtils.getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_SQL)){
      preparedStatement.setString(1, user.getName());
      preparedStatement.setString(2, user.getEmail());
      preparedStatement.setString(3, user.getCountry());
      preparedStatement.setInt(4, user.getId());
      rowUpdated = preparedStatement.executeUpdate() > 0;
    }catch (SQLException e) {
      e.printStackTrace();
    }
    return rowUpdated;
  }
}
