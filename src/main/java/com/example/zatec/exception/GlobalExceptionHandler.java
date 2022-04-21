package com.example.zatec.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.net.UnknownHostException;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorDetails> handleApplicationException(ApplicationException exception, HttpServletResponse response, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpServletResponse.SC_NOT_ACCEPTABLE, HttpStatus.NOT_ACCEPTABLE.toString(), exception.getMessage(), request.getDescription(false));
        return ResponseEntity.ok(errorDetails);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> handleApplicationException(NotFoundException exception, HttpServletResponse response, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpServletResponse.SC_NOT_FOUND, HttpStatus.NOT_FOUND.toString(), exception.getMessage(), request.getDescription(false));
        return ResponseEntity.ok(errorDetails);
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<?> handleEntityNotFoundException(UnknownHostException exception, HttpServletResponse response, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpServletResponse.SC_GATEWAY_TIMEOUT, HttpStatus.GATEWAY_TIMEOUT.toString(),
                "Unable to reach the server at "+exception.getMessage()+". Start by checking your internet connectivity", request.getDescription(false));
        return ResponseEntity.ok(errorDetails);
    }


    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> handleEntityNotFoundException(HttpClientErrorException exception, HttpServletResponse response, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpServletResponse.SC_BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),
                exception.getMessage(), request.getDescription(false));
        return ResponseEntity.ok(errorDetails);
    }


    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<ErrorDetails> handleApplicationException(MismatchedInputException exception, HttpServletResponse response, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), HttpServletResponse.SC_BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(),
                exception.getMessage(), request.getDescription(false));
        return ResponseEntity.ok(errorDetails);
    }

}