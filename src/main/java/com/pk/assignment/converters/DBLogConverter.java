package com.pk.assignment.converters;

import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.assignment.Beans.Customer;
import com.pk.assignment.Beans.ErrorResponse;
import com.pk.assignment.model.AuditLog;
import com.pk.assignment.model.ErrorLog;

public class DBLogConverter {

    @Autowired
    ObjectMapper mapper;

    public AuditLog convert(Customer customer) {
        AuditLog auditLog = new AuditLog();

        try {
            String customerNumber = customer.getCustomerNumber();
            String payload = mapper.writeValueAsString(customer);

            auditLog.setCustomerNumber(customerNumber);
            auditLog.setPayload(payload);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return auditLog;
    }

    public ErrorLog convert(ErrorResponse errorResponse, Customer customer)
            throws JsonProcessingException {
        ErrorLog errorLog = new ErrorLog();
        String errorType = errorResponse.getErrorType();
        String errorDiscription = errorResponse.getMessage();
        String payload = mapper.writeValueAsString(customer);
        errorLog.setErrorType(errorType);
        errorLog.setErrorDiscription(errorDiscription);
        errorLog.setPayload(payload);
        return errorLog;
    }
}
