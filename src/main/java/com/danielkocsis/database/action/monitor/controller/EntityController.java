package com.danielkocsis.database.action.monitor.controller;

import com.danielkocsis.database.action.monitor.model.DatabaseEntity;
import com.danielkocsis.database.action.monitor.repository.DatabaseEntityRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Api(value = "/api", description = "Helper REST API to interact with the database", tags = {"/api"})
public class EntityController {

  private final DatabaseEntityRepository repository;

  @GetMapping
  @ApiOperation("Lists all the persisted entities from the database.")
  public List<DatabaseEntity> listEntities() {
    return repository.findAll();
  }

  @PostMapping
  @ApiOperation("Inserts and entity into the database.")
  public ResponseEntity insertEntity(
      @ApiParam("Represents the entity to insert") @RequestBody DatabaseEntity databaseEntity) {

    repository.save(databaseEntity);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
