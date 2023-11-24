package com.mailmanager.mvc.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
  private static Connection connection;

  public static Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
      try {
        String path = new File(".").getCanonicalPath();
        String stringConnection = String.format("jdbc:sqlite:%s/mailmanager.db", path);
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(stringConnection);
      }
      catch (Exception exception) {
        System.err.println(exception.getClass().getName() + ": " + exception.getMessage());
        System.exit(1);
      }
    }
    return connection;
  }
}
