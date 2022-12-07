package com.test.repository;

import com.test.models.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventRepository {

  private static final Logger logger = LoggerFactory.getLogger(EventRepository.class);

  private Connection connection;

  public EventRepository() {
    try {
      this.connection = DriverManager.getConnection("jdbc:hsqldb:file:db/logdb", "SA", "");
      this.createTable();
      this.clearTable();
    } catch (SQLException ex) {
      logger.error("Failed to create connection", ex);
    }
  }

  private void createTable() throws SQLException {
    try {
      String createEvents =
          "CREATE TABLE IF NOT EXISTS Event (id VARCHAR(50) NOT NULL, duration FLOAT NOT NULL, type VARCHAR(50), host " + "VARCHAR(50), " + "alert BOOLEAN NOT NULL)";
      this.connection.createStatement().executeUpdate(createEvents);
    } catch (SQLException ex) {
      logger.error("Failed to create table", ex);
    }
  }

  private void clearTable() throws SQLException {
    try {
      String dropEvents = "TRUNCATE TABLE Event";
      this.connection.createStatement().executeUpdate(dropEvents);
    } catch (SQLException ex) {
      logger.error("Failed to drop table", ex);
    }
  }

  public void save(Event event) {
    try {
      String addEvent = "INSERT INTO Event (id, duration, type, host, alert)  VALUES (?, ?, ?, ?, ?)";

      PreparedStatement preparedStatement = connection.prepareStatement(addEvent);
      preparedStatement.setString(1, event.getId());
      preparedStatement.setFloat(2, event.getDuration());
      preparedStatement.setString(3, event.getType());
      preparedStatement.setString(4, event.getHost());
      preparedStatement.setBoolean(5, event.isAlert());

      preparedStatement.executeUpdate();

    } catch (SQLException ex) {
      logger.error("Failed to insert record: {}", event, ex);
    }
  }
}
