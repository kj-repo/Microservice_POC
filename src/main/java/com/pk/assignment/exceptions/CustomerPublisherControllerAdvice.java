package com.pk.assignment.exceptions;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.hql.internal.ast.ErrorReporter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.google.common.net.HttpHeaders;
import com.pk.assignment.Beans.ErrorResponse;

@ControllerAdvice
public class CustomerPublisherControllerAdvice {

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<Object> handleException(
            ServletRequestBindingException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("400");
        errorResponse.setMessage("");
        errorResponse.setErrorType(Exception.class.getSimpleName());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(
            Exception ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus("401");
        errorResponse.setMessage("");
        errorResponse.setErrorType(Exception.class.getSimpleName());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
