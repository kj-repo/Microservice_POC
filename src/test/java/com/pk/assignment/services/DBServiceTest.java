package com.pk.assignment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.assignment.Beans.Address;
import com.pk.assignment.Beans.Customer;
import com.pk.assignment.Beans.Customer.CustomerStatusEnum;
import com.pk.assignment.Beans.ErrorResponse;
import com.pk.assignment.converters.AuditLogConverter;
import com.pk.assignment.converters.ErrorLogConverter;
import com.pk.assignment.model.AuditLog;
import com.pk.assignment.model.ErrorLog;
import com.pk.assignment.repository.AuditLogRepository;
import com.pk.assignment.repository.ErrorLogRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DBServiceTest {
    @MockBean
    private AuditLogRepository auditLogRepository;

    @MockBean
    private AuditLogConverter auditLogConverter;

    @MockBean
    private ErrorLogRepository errorLogRepository;

    @MockBean
    private ErrorLogConverter errorLogConverter;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    @Autowired
    private DBLogServiceImpl dBLogServiceImpl;


    @Test
    public void testLogMessageWhenPassingValidCustomerShouldAddAuditLogObjectinDB()
            throws Exception {
        Mockito.when(auditLogConverter.convert(Mockito.any(Customer.class)))
                .thenReturn(createAuditLog());
        Mockito.when(auditLogRepository.save(Mockito.any(AuditLog.class)))
                .thenReturn(getMockAuditLog());

        AuditLog result = dBLogServiceImpl.logMessage(createCustomer());

        assertNotNull(result);
        assertEquals(10L, result.getId());
    }

    @Test
    public void testlogErrorWhenPassingValidCustomerAndErrorResponseShouldAddErrorLogObjectinDB()
            throws Exception {
        ErrorLog errorLog = createErrorLog();
        ErrorLog mockErrorLog = getMockErrorLog(errorLog);
        Mockito.when(
                errorLogConverter.convert(Mockito.anyString(), Mockito.any(ErrorResponse.class)))
                .thenReturn(errorLog);
        Mockito.when(errorLogRepository.save(Mockito.any(ErrorLog.class))).thenReturn(mockErrorLog);
        ErrorLog result = dBLogServiceImpl.logError(createCustomer(), createErrorResponse());
        assertNotNull(result);
        assertEquals(12L, result.getId());
    }

    private AuditLog createAuditLog() throws JsonProcessingException {
        Customer customer = createCustomer();
        AuditLog auditLog = new AuditLog();
        auditLog.setCustomerNumber((String) customer.getCustomerNumber());
        auditLog.setPayload(objectMapper.writeValueAsString(customer));
        return auditLog;
    }

    private AuditLog getMockAuditLog() throws JsonProcessingException {
        AuditLog auditLog = createAuditLog();
        auditLog.setId(10L);
        return auditLog;
    }

    private ErrorLog getMockErrorLog(ErrorLog errorLog) {
        errorLog.setId(12L);
        return errorLog;
    }

    private ErrorLog createErrorLog() throws JsonProcessingException {
        Customer customer = createCustomer();
        ErrorResponse errorResponse = createErrorResponse();
        ErrorLog errorLog = new ErrorLog();
        errorLog.setErrorDiscription(errorResponse.getMessage());
        errorLog.setErrorType(errorResponse.getErrorType());
        errorLog.setPayload(objectMapper.writeValueAsString(customer));

        return errorLog;
    }

    private ErrorResponse createErrorResponse() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorType("MethodArgumentNotValidException");
        errorResponse.setMessage("[email: Email should be valid]");
        errorResponse.setStatus("ERROR");
        return errorResponse;
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
