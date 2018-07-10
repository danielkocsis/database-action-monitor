package com.danielkocsis.database.action.monitor.model;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class DatabaseEntity {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @JsonIgnore
  private long id;

  private String name;

  @JsonIgnore
  private boolean isNew = true;

  public DatabaseEntity(String name) {
    this.name = name;
  }

}
