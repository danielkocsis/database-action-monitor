package com.danielkocsis.database.action.monitor.repository;

import com.danielkocsis.database.action.monitor.model.DatabaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseEntityRepository extends JpaRepository<DatabaseEntity, Integer> {

}
