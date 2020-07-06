package com.pk.assignment.exceptions;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.pk.assignment.constants.PublisherConstant;
import com.pk.assignment.domain.Customer;
import com.pk.assignment.domain.ErrorResponse;
import com.pk.assignment.services.DBLogServiceImpl;

@ControllerAdvice
public class CustomerPublisherControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(CustomerPublisherControllerAdvice.class);
    private static String customerAttribute = "Customer";

    @Autowired
    DBLogServiceImpl dbLogService;


    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<Object> handleException(ServletRequestBindingException ex,
            HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(PublisherConstant.ERROR_STATUS);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrorType(ServletRequestBindingException.class.getSimpleName());
        dbLogService.logError((Customer) request.getAttribute(customerAttribute), errorResponse);
        log.error("Something went wrong: {0} ",errorResponse.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleException(AccessDeniedException ex,
            HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(PublisherConstant.ERROR_STATUS);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrorType(ex.getClass().getSimpleName());
        dbLogService.logError((Customer) request.getAttribute(customerAttribute), errorResponse);
        log.error("Something went wrong: {0} ",errorResponse.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleException(IllegalArgumentException ex,
            HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(PublisherConstant.ERROR_STATUS);
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrorType(IllegalArgumentException.class.getSimpleName());
        dbLogService.logError((Customer) request.getAttribute(customerAttribute), errorResponse);
        log.error("Something went wrong: {0} ",errorResponse.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(PublisherConstant.ERROR_STATUS);
        errorResponse.setMessage(errors.toString());
        errorResponse.setErrorType(MethodArgumentNotValidException.class.getSimpleName());
        dbLogService.logError((Customer) request.getAttribute(customerAttribute), errorResponse);
        log.error("Something went wrong: {0} ",errorResponse.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(PublisherConstant.ERROR_STATUS);
        errorResponse.setMessage(ex.toString());
        errorResponse.setErrorType(Exception.class.getSimpleName());
        dbLogService.logError((Customer) request.getAttribute(customerAttribute), errorResponse);
        log.error("Something went wrong: {0} ",errorResponse.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
