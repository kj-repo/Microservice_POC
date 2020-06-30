package com.pk.assignment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import com.pk.assignment.Beans.Address;
import com.pk.assignment.Beans.Customer;
import com.pk.assignment.Beans.Customer.CustomerStatusEnum;
import com.pk.assignment.converters.AuditLogConverter;
import com.pk.assignment.model.AuditLog;
import com.pk.assignment.repository.AuditLogRepository;


@RunWith(MockitoJUnitRunner.class)
public class AuditLogServiceTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    @Mock
    private AuditLogConverter auditLogConverter;

    @InjectMocks
    private AuditLogService auditLogService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        auditLogService = new AuditLogService();
      
    }

    @Test
    public void testLogMessageWhenPassingValidCustomerShouldAddAuditLogObjectinDB() {
        when(auditLogConverter.convert(Mockito.any(Customer.class))).thenReturn(createAuditLog());
        when(auditLogRepository.save(Mockito.any(AuditLog.class))).thenReturn(getMockAuditLog());

        AuditLog result = auditLogService.logMessage(createCustomer());
        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    private AuditLog createAuditLog() {
        Customer customer = createCustomer();
        AuditLog auditLog = new AuditLog();
        auditLog.setCustomerNumber(customer.getCustomerNumber());
        auditLog.setPayload(customer.toString());
        return auditLog;
    }

    private AuditLog getMockAuditLog() {
        AuditLog auditLog = createAuditLog();
        auditLog.setId(10L);
        return auditLog;
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setAddress(createAddress());
        customer.setBirthdate("2-12-2012");
        customer.setCountry("India");
        customer.setCountryCode("IN");
        customer.setCustomerNumber("C000001");
        customer.setEmail("email@gmail.com");
        customer.setCustomerStatus(CustomerStatusEnum.RESTORED);
        customer.setFirstName("Foo");
        customer.setLastName("boo");
        customer.setMobileNumber("1234567890");
        return customer;

    }

    private Address createAddress() {
        Address address = new Address();
        address.setAddressLine1("AddressLine1");
        address.setAddressLine2("AddressLine2");
        address.setPostalCode("12345");
        address.setStreet("Street");
        return address;
    }
}
