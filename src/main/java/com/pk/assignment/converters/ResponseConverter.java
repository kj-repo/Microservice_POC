package com.pk.assignment.converters;

import org.springframework.core.convert.converter.Converter;
import com.pk.assignment.Beans.Customer;
import com.pk.assignment.Beans.ErrorResponse;
import com.pk.assignment.Beans.SuccessResponse;

public class ResponseConverter implements Converter<String, SuccessResponse> {
    @Override
    public SuccessResponse convert(String message) {
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage(message);
        successResponse.setStatus("SUCCESS");
        return successResponse;

    }

    public ErrorResponse convert(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus("ERROR");
        errorResponse.setErrorType(exception.getLocalizedMessage());
        return null;

    }
}
