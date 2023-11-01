package com.trnd.trndapi.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String > handleMethodArgumentException(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String, String> errorMap = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach( fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Map<String, String> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage",ex.getMostSpecificCause().getMessage());
        return errorMap;
    }
}
