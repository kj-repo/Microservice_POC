package com.pk.assignment.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.pk.assignment.Beans.Address;
import com.pk.assignment.Beans.Customer;
import com.pk.assignment.Beans.Customer.CustomerStatusEnum;

@Tag("unit")
public class CustomerMaskConverterTest {

    private CustomerMaskConverter converterMaskConverter;

    @BeforeEach
    public void setup() {
        converterMaskConverter = new CustomerMaskConverter();
    }

    @Test
    public void testConvertWhenPassingValidCustomerShouldApplyMasking() {
        Customer result = converterMaskConverter.convert(createCustomer());
        assertNotNull(result);
        assertEquals("***0001", result.getCustomerNumber());
        assertEquals("ema**@gmail.com", result.getEmail());
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
