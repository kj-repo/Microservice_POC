package com.pk.assignment.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pk.assignment.Beans.Customer;
import com.pk.assignment.Beans.SuccessResponse;
import com.pk.assignment.converters.CustomerMaskConverter;
import com.pk.assignment.converters.ResponseConverter;
import com.pk.assignment.services.AuditLogService;

@RestController
@RequestMapping(value = "customer")
public class CustomerPublisherController {

    private static final Logger log = LoggerFactory.getLogger(CustomerPublisherController.class);

    @Autowired
    AuditLogService auditLogService;

    @Autowired
    CustomerMaskConverter customerMaskConverter;

    @Autowired
    ResponseConverter responseConverter;


    @PostMapping(path = "create", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> addCustomer(
            @RequestHeader(value = "Autherization", required = true) String authorization,
            @RequestHeader(value = "Activity-Id", required = false) String activityId,
            @RequestHeader(value = "Application-Id", required = false) String applicationId,
            @Valid @RequestBody Customer customer) {
        System.out.println(customer.getCustomerNumber());
        Customer maskCustomer = customerMaskConverter.convert(customer);
        log.info("Request Customer Data :" + maskCustomer);
        auditLogService.logMessage(customer);
        SuccessResponse response = responseConverter.convert("Customer Added Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
