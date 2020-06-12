package com.dolapo.usermanagement.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {

  private static final String jdbcURL = "jdbc:mysql://localhost:3306/java_demo?useSSL=false";
  private static final String jdbcUsername = "root";
  private static final String jdbcPassword = "root";

  public static Connection getConnection() {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return connection;
  }

  private static void printSQLEXCEPTION(SQLException ex){

    for(Throwable e : ex){
      if(e instanceof SQLException){
        e.printStackTrace(System.err);
        System.err.println("SqlState " + ((SQLException) e).getSQLState());
        System.err.println("Error code " + ((SQLException) e).getErrorCode());
        System.err.println("Message " + e.getMessage());
        Throwable t = ex.getCause();
        while (t != null) {
          System.out.println("Cause: " + t);
          t = t.getCause();
        }
      }
    }
  }
}
