package com.mailmanager.mvc.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mailmanager.mvc.models.Email;
import com.mailmanager.mvc.utils.DbConnector;

public class Service {
  public List<Email> getAll() throws SQLException {
    Connection connection = DbConnector.getConnection();
    List<Email> emails = new ArrayList<>();
    Statement statement = connection.createStatement();
    ResultSet result = statement.executeQuery("SELECT * FROM emails;");
    
    while (result.next())
      emails.add(new Email(result.getString(1)));
    
    statement.close();
    connection.close();
    
    return emails;
  }

  public Email get(String address) throws SQLException {
    Connection connection = DbConnector.getConnection();
    Statement statement = connection.createStatement();
    ResultSet result = statement.executeQuery(
      String.format("SELECT * FROM emails WHERE address=%s;", address)
    );
    
    if (result.next())
      return new Email(result.getString(1));

    statement.close();
    connection.close();
    
    return null;
  }
  
  public void add(Email email) throws Exception {
    if (this.get(email.getAddress()) != null)
      throw new Exception(String.format("Addresse %s est deja inscrite", email.getAddress()));
  
    Connection connection = DbConnector.getConnection();
    Statement statement = connection.createStatement();
    statement.executeUpdate(
      String.format("INSERT INTO emails VALUES('%s');", email.getAddress())
    );
    
    statement.close();
    connection.close();
  }

  public void delete(String address) throws Exception {
    if (this.get(address) == null)
      throw new Exception(String.format("Addresse %s non inscrite", address));
  
    Connection connection = DbConnector.getConnection();
    Statement statement = connection.createStatement();
    statement.executeUpdate(
      String.format("DELETE FROM emails WHERE address='%s';", address)
    );
    
    statement.close();
    connection.close();
  }
}
