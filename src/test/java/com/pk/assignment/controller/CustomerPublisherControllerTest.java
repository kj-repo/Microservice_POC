package com.pk.assignment.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pk.assignment.Beans.Address;
import com.pk.assignment.Beans.Customer;
import org.springframework.security.access.AccessDeniedException;
import com.pk.assignment.Beans.Customer.CustomerStatusEnum;
import com.pk.assignment.Beans.ErrorResponse;
import com.pk.assignment.Beans.SuccessResponse;
import com.pk.assignment.converters.CustomerMaskConverter;
import com.pk.assignment.converters.ResponseConverter;
import com.pk.assignment.model.AuditLog;
import com.pk.assignment.services.DBLogServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(CustomerPublisherController.class)
@AutoConfigureMockMvc
public class CustomerPublisherControllerTest {

    private static String tocken = "Bearer be9737ac-9baa-473b-b3a6-8a4ff6711f1e";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    DBLogServiceImpl dBLogService;

    @MockBean
    CustomerMaskConverter customerMaskConverter;

    @MockBean
    ResponseConverter responseConverter;

    @MockBean
    ClientDetailsService clientDetailService;

    @Before
    private void setup() throws Exception {
        tocken = obtainAccessToken("user1", "abc123");
    }

    @Test
    public void testCreateCustomerWhenValidCustomerSendthenReturnSuccessResponse()
            throws Exception {
        String mockCustomer = objectMapper.writeValueAsString(createCustomer());
        when(dBLogService.logMessage(Mockito.any(Customer.class))).thenReturn(createAuditLog());
        when(customerMaskConverter.convert(Mockito.any(Customer.class)))
                .thenReturn(createMaskCustomer());
        when(responseConverter.convert(Mockito.anyString())).thenReturn(createResponse());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/create")
                .header("Authorization", tocken).header("Activity-Id", "1")
                .header("Application-Id", "1").accept(MediaType.APPLICATION_JSON)
                .content(mockCustomer).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        SuccessResponse successResponse =
                objectMapper.readValue(response.getContentAsString(), SuccessResponse.class);
        assertEquals("SUCCESS", successResponse.getStatus());
        assertEquals("Customer Added Successfully", successResponse.getMessage());
    }

    @Test
    public void testCreateCustomerWhenAutherizationMissingthenReturnErrorResponse()
            throws Exception {

        String mockCustomer = objectMapper.writeValueAsString(createCustomer());
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/customer/create").header("Activity-Id", "1")
                        .header("Application-Id", "1").accept(MediaType.APPLICATION_JSON)
                        .content(mockCustomer).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        ErrorResponse errorResponse =
                objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertEquals("ERROR", errorResponse.getStatus());
        assertEquals("Missing request header 'Authorization' for method parameter of type String",
                errorResponse.getMessage());
        assertEquals("ServletRequestBindingException", errorResponse.getErrorType());

    }

    @Test
    public void testCreateCustomerWhenInvalidEmailthenReturnErrorResponse() throws Exception {
        Customer customer = createCustomer().email("abcd@d@gamil.com");
        String mockCustomer = objectMapper.writeValueAsString(customer);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/create")
                .header("Activity-Id", "1").header("Authorization", tocken)
                .header("Application-Id", "1").accept(MediaType.APPLICATION_JSON)
                .content(mockCustomer).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        ErrorResponse errorResponse =
                objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertEquals("ERROR", errorResponse.getStatus());
        assertEquals("[email: Email should be valid]", errorResponse.getMessage());
        assertEquals("MethodArgumentNotValidException", errorResponse.getErrorType());
    }

    @Test
    public void testCreateCustomerWhenInvalidTokenReturnErrorResponse() throws Exception {
        when(dBLogService.logMessage(Mockito.any(Customer.class)))
                .thenThrow(AccessDeniedException.class);

        Customer customer = createCustomer();
        String mockCustomer = objectMapper.writeValueAsString(customer);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/create")
                .header("Activity-Id", "1").header("Authorization", tocken).content(mockCustomer)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        ErrorResponse errorResponse =
                objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertEquals("ERROR", errorResponse.getStatus());
        assertEquals("AccessDeniedException", errorResponse.getErrorType());

    }

    @Test
    public void testCreateCustomerWhenIllegalArgumentsReturnErrorResponse() throws Exception {
        when(dBLogService.logMessage(Mockito.any(Customer.class)))
                .thenThrow(IllegalArgumentException.class);
        Customer customer = createCustomer();
        String mockCustomer = objectMapper.writeValueAsString(customer);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/create")
                .header("Activity-Id", "1").header("Authorization", "909b-08aaec3b3ab1")
                .content(mockCustomer).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        ErrorResponse errorResponse =
                objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertEquals("ERROR", errorResponse.getStatus());
        assertEquals("IllegalArgumentException", errorResponse.getErrorType());
    }

    @Test
    public void testCreateCustomerWhenIllegalArgumentReturnErrorResponse() throws Exception {
        when(dBLogService.logMessage(Mockito.any(Customer.class)))
                .thenThrow(IllegalArgumentException.class);

        Customer customer = createCustomer();
        String mockCustomer = objectMapper.writeValueAsString(customer);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/create")
                .header("Activity-Id", "1").header("Authorization", "909b-08aaec3b3ab1")
                .content(mockCustomer).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        ErrorResponse errorResponse =
                objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertEquals("ERROR", errorResponse.getStatus());
        assertEquals("IllegalArgumentException", errorResponse.getErrorType());

    }

    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);
        ResultActions result = mockMvc
                .perform(post("/oauth/token").params(params)
                        .with(httpBasic("my-trusted-client", "secret"))
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();
        System.out.println(resultString);
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }


    private SuccessResponse createResponse() {
        SuccessResponse response = new SuccessResponse();
        response.setMessage("Customer Added Successfully");
        response.setStatus("SUCCESS");
        return response;
    }

    private Customer createMaskCustomer() {
        Customer maskCustomer = createCustomer();
        maskCustomer.setCustomerNumber("***0001");
        maskCustomer.setEmail("ema**@gmail.com");
        maskCustomer.setBirthdate("**-**-2019");
        return maskCustomer;
    }

    private AuditLog createAuditLog() {
        Customer customer = createCustomer();
        AuditLog auditLog = new AuditLog();
        auditLog.setCustomerNumber((String) customer.getCustomerNumber());
        auditLog.setPayload(customer.toString());
        return auditLog;
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setAddress(createAddress());
        customer.setBirthdate("02-12-2019");
        customer.setCountry("India");
        customer.setCountryCode("IN");
        customer.setCustomerNumber("C000001");
        customer.setEmail("email@gmail.com");
        customer.setCustomerStatus(CustomerStatusEnum.RESTORED);
        customer.setFirstName("Foofoofoof");
        customer.setLastName("boobooboob");
        customer.setMobileNumber("9234567890");
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
