package com.pk.assignment.services;

import org.springframework.stereotype.Service;
import com.pk.assignment.domain.Customer;
import com.pk.assignment.domain.ErrorResponse;
import com.pk.assignment.model.AuditLog;
import com.pk.assignment.model.ErrorLog;

@Service
public interface DBLogService {

    public AuditLog logMessage(Customer customer);

    public ErrorLog logError(Customer request, ErrorResponse errorResponse);
}
