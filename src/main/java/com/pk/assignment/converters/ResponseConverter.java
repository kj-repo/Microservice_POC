package com.pk.assignment.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.pk.assignment.domain.SuccessResponse;

@Component
public class ResponseConverter implements Converter<String, SuccessResponse> {
    @Override
    public SuccessResponse convert(String message) {
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage(message);
        successResponse.setStatus("SUCCESS");
        return successResponse;

    }
}
