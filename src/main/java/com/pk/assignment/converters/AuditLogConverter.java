package com.pk.assignment.converters;

import org.springframework.stereotype.Component;
import com.pk.assignment.Beans.Customer;
import com.pk.assignment.model.AuditLog;

@Component
public class AuditLogConverter {

    public AuditLog convert(Customer customer) {
        AuditLog auditLog = new AuditLog();
        String customerNumber = (String) customer.getCustomerNumber();
        String payload = customer.toString();
        auditLog.setCustomerNumber(customerNumber);
        auditLog.setPayload(payload);
        return auditLog;
    }


}
