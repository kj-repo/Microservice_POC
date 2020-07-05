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
import com.pk.assignment.Beans.Customer;
import com.pk.assignment.Beans.ErrorResponse;
import com.pk.assignment.controller.CustomerPublisherController;
import com.pk.assignment.services.DBLogServiceImpl;

@ControllerAdvice
public class CustomerPublisherControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(CustomerPublisherController.class);

    @Autowired
    DBLogServiceImpl dbLogService;


    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<Object> handleException(ServletRequestBindingException ex,
            HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrorType(ServletRequestBindingException.class.getSimpleName());
        dbLogService.logError((Customer) request.getAttribute("Customer"), errorResponse);
        log.error(errorResponse.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleException(AccessDeniedException ex,
            HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrorType(AccessDeniedException.class.getSimpleName());
        dbLogService.logError((Customer) request.getAttribute("Customer"), errorResponse);
        log.error(errorResponse.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleException(IllegalArgumentException ex,
            HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrorType(IllegalArgumentException.class.getSimpleName());
        dbLogService.logError((Customer) request.getAttribute("Customer"), errorResponse);
        log.error(errorResponse.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(errors.toString());
        errorResponse.setErrorType(MethodArgumentNotValidException.class.getSimpleName());
        dbLogService.logError((Customer) request.getAttribute("Customer"), errorResponse);
        log.error(errorResponse.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.toString());
        errorResponse.setErrorType(Exception.class.getSimpleName());
        dbLogService.logError((Customer) request.getAttribute("Customer"), errorResponse);
        log.error(errorResponse.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
