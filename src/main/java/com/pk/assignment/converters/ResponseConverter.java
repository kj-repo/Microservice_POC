package com.pk.assignment.converters;

import com.pk.assignment.Beans.ErrorResponse;
import com.pk.assignment.Beans.SuccessResponse;

public class ResponseConverter {
    public SuccessResponse convert(String message){
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage(message);
        successResponse.setStatus("SUCCESS");
        return null;
  
    }
    public ErrorResponse convert(Exception exception){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setStatus("ERROR");
        errorResponse.setErrorType(exception.getLocalizedMessage());
        return null;
  
    }
}
