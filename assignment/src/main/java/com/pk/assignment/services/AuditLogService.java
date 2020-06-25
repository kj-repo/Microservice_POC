package com.pk.assignment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pk.assignment.model.AuditLog;
import com.pk.assignment.model.ErrorLog;
import com.pk.assignment.repository.AuditLogRepository;
import com.pk.assignment.repository.ErrorLogRepository;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private ErrorLogRepository errorLogRepository;

    public void logMessage(AuditLog auditLog) {
        auditLogRepository.save(auditLog);
    }

    public void logError(ErrorLog errorLog) {
        errorLogRepository.save(errorLog);
    }
}
