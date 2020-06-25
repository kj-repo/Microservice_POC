package com.pk.assignment.controller;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "customer")
public class CustomerPublisherController {

    private static final Logger log = LoggerFactory.getLogger(CustomerPublisherController.class);

    @PostMapping(path = "create-customer", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> addCustomer(
            @ApiParam(value = "Autherization") @RequestHeader(value = "Autherization",
                    required = false) String authorization,
            @ApiParam(value = "an activity id") @RequestHeader(value = "Activity-Id",
                    required = false) String activityId,
            @ApiParam(value = "an application id") @RequestHeader(value = "Application-Id",
                    required = false) String applicationId,
            @ApiParam(value = "customer to add") @Valid @RequestBody Customer customer) {
        
        return new ResponseEntity<SuccessResponse>(HttpStatus.OK);
    }

}
