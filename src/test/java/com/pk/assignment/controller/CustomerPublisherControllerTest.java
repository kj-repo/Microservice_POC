package com.pk.assignment.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.pk.assignment.Beans.Address;
import com.pk.assignment.Beans.Customer;
import com.pk.assignment.Beans.Customer.CustomerStatusEnum;
import com.pk.assignment.Beans.SuccessResponse;
import com.pk.assignment.converters.CustomerMaskConverter;
import com.pk.assignment.converters.ResponseConverter;
import com.pk.assignment.model.AuditLog;
import com.pk.assignment.services.AuditLogService;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(CustomerPublisherController.class)
public class CustomerPublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AuditLogService auditLogService;

    @MockBean
    CustomerMaskConverter customerMaskConverter;

    @MockBean
    ResponseConverter responseConverter;
    
    @MockBean
    ClientDetailsService clientDetailService;
    
    @Test
    public void testCreateCustomerWhenValidCustomerSendthenReturnSuccessResponse()
      throws Exception {
        String mockCustomer = "{}";
        when(auditLogService.logMessage(Mockito.any(Customer.class))).thenReturn(createAuditLog());
        when(customerMaskConverter.convert(Mockito.any(Customer.class))).thenReturn(createMaskCustomer());
        when(responseConverter.convert(Mockito.anyString())).thenReturn(createResponse());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/create")
                .header("Autherization", "68cb4d38-58bc-41f5-909b-08aaec3b3ab1")
                .header("Activity-Id", "1")
                .header("Application-Id", "1")
                .accept(MediaType.APPLICATION_JSON).content(mockCustomer)
                .contentType(MediaType.APPLICATION_JSON);
        

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals("{\"status\":\"SUCCESS\",\"message\":\"Customer Added Successfully\"}", response.getContentAsString());

       
    }
    @Test
    public void testCreateCustomerWhenAutherizationMissingthenReturnErrorResponse()
      throws Exception {
        String mockCustomer = "{}";

        when(auditLogService.logMessage(Mockito.any(Customer.class))).thenReturn(createAuditLog());
        when(customerMaskConverter.convert(Mockito.any(Customer.class))).thenReturn(createMaskCustomer());
        when(responseConverter.convert(Mockito.anyString())).thenReturn(createResponse());

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/create")
                .header("Activity-Id", "1")
                .header("Application-Id", "1")
                .accept(MediaType.APPLICATION_JSON).content(mockCustomer)
                .contentType(MediaType.APPLICATION_JSON);
        

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals("{\"status\":\"ERROR\",\"message\":\"Missing request header 'Autherization' for method parameter of type String\",\"error_type\":\"ServletRequestBindingException\"}", response.getContentAsString());

       
    }
    private SuccessResponse createResponse() {
        SuccessResponse response  = new SuccessResponse();
        response.setMessage("Customer Added Successfully");
        response.setStatus("SUCCESS");
        return response;
    }
    private Customer createMaskCustomer() {
        Customer maskCustomer = createCustomer();
        maskCustomer.setCustomerNumber("***0001");
        maskCustomer.setEmail("ema**@gmail.com");
        return maskCustomer;
    }
    
    private AuditLog createAuditLog() {
        Customer customer = createCustomer();
        AuditLog auditLog = new AuditLog();
        auditLog.setCustomerNumber(customer.getCustomerNumber());
        auditLog.setPayload(customer.toString());
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
