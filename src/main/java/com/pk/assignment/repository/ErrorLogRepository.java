package com.pk.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.pk.assignment.model.ErrorLog;

@Repository
public interface ErrorLogRepository extends CrudRepository<ErrorLog, Long> {

}
