package com.pk.assignment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pk.assignment.Beans.Customer;
import com.pk.assignment.converters.AuditLogConverter;
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
    
    @Autowired
    AuditLogConverter auditLogConverter;

    public AuditLog logMessage(Customer customer) {
        AuditLog auditLog =  auditLogConverter.convert(customer);
        return auditLogRepository.save(auditLog);
        
    }

    public ErrorLog logError(ErrorLog errorLog) {
        return errorLogRepository.save(errorLog);
    }
}
