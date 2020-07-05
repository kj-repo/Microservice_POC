package com.pk.assignment.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.pk.assignment.Beans.Customer;
import com.pk.assignment.constants.PublisherConstant;

@Component
public class CustomerMaskConverter implements Converter<Customer, Customer> {

    @Override
    public Customer convert(Customer source) {
        Customer customer = new Customer();
        customer.setCustomerNumber(((String) source.getCustomerNumber())
                .replaceAll(PublisherConstant.NUMBER_MASK, "*"));
        customer.setFirstName(source.getFirstName());
        customer.setLastName(source.getLastName());
        customer.setCustomerStatus(source.getCustomerStatus());
        customer.setAddress(source.getAddress());
        customer.setBirthdate(source.getBirthdate().replaceAll(PublisherConstant.DATE_MASK, "*"));
        customer.setCountry(source.getCountry());
        customer.setCountryCode(source.getCountryCode());
        customer.setEmail(source.getEmail().replaceAll(PublisherConstant.EMAIL_MASK, "*"));
        customer.setMobileNumber(source.getMobileNumber());

        return customer;
    }

}
