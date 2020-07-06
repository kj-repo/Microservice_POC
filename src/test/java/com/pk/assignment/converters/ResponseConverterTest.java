package com.pk.assignment.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import com.pk.assignment.domain.SuccessResponse;

@Tag("unit")
public class ResponseConverterTest {

    private ResponseConverter responseConverter;

    @BeforeEach
    public void setup() {
        responseConverter = new ResponseConverter();
    }

    @Test
    public void testConvertWhenPassingValidStringMessageShouldReturnSuccessResponseObject() {
        String message = "Successfully Added";
        SuccessResponse response = responseConverter.convert(message);
        assertNotNull(response);
        assertEquals("Successfully Added", response.getMessage());
        assertEquals("SUCCESS", response.getStatus());
    }

}
