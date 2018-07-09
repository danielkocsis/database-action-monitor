package com.danielkocsis.database.action.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is a Spring Boot application uses Spring Integration JDBC poller to monitor a table and
 * sends WebSocket broadcast messages about every INSERT events.
 */
@SpringBootApplication
public class DatabaseActionMonitorApplication {

  public static void main(String[] args) {
    SpringApplication.run(DatabaseActionMonitorApplication.class, args);
  }

}
