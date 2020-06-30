package com.pk.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.pk.assignment.model.AuditLog;

@Repository
public interface AuditLogRepository extends CrudRepository<AuditLog, Long> {

}
