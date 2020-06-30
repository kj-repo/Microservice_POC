package com.pk.assignment.converters;

import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.assignment.Beans.Customer;
import com.pk.assignment.Beans.ErrorResponse;
import com.pk.assignment.model.AuditLog;
import com.pk.assignment.model.ErrorLog;

public class AuditLogConverter {

    public AuditLog convert(Customer customer) {
        AuditLog auditLog = new AuditLog();
        String customerNumber = customer.getCustomerNumber();
        String payload = customer.toString();
        auditLog.setCustomerNumber(customerNumber);
        auditLog.setPayload(payload);
        return auditLog;
    }


}
