package com.pk.assignment.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.assignment.Beans.Address;
import com.pk.assignment.Beans.Customer;
import com.pk.assignment.Beans.Customer.CustomerStatusEnum;
import com.pk.assignment.Beans.ErrorResponse;
import com.pk.assignment.model.ErrorLog;

@Tag("unit")
public class ErrorLogConverterTest {

    private ErrorLogConverter errorLogConverter;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        errorLogConverter = new ErrorLogConverter();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testConvertWhenPassingValidCustomerAndErrorResponseShouldReturnErrorLogObject()
            throws Exception {
        String payload = objectMapper.writeValueAsString(createCustomer());
        ErrorLog result = errorLogConverter.convert(payload, createErrorResponse());
        assertNotNull(result);
        assertEquals("MethodArgumentNotValidException", result.getErrorType());
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
