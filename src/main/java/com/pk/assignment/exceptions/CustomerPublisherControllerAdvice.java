package com.pk.assignment.exceptions;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.hql.internal.ast.ErrorReporter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import com.google.common.net.HttpHeaders;
import com.pk.assignment.Beans.ErrorResponse;

@ControllerAdvice
public class CustomerPublisherControllerAdvice {

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<Object> handleException(
            ServletRequestBindingException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrorType(ServletRequestBindingException.class.getSimpleName());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
   
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleException(
            AccessDeniedException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrorType(AccessDeniedException.class.getSimpleName());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleException(
            IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setErrorType(IllegalArgumentException.class.getSimpleName());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.joining(","));
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(errors);   
        errorResponse.setErrorType(MethodArgumentNotValidException.class.getSimpleName());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(
           Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("ERROR");
        errorResponse.setMessage(ex.toString());
        errorResponse.setErrorType(Exception.class.getSimpleName());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
