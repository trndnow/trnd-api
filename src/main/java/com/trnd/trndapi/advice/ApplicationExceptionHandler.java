package com.trnd.trndapi.advice;

import com.trnd.trndapi.dto.ResponseDto;
import com.trnd.trndapi.enums.ERole;
import com.trnd.trndapi.exception.MerchantNoFoundException;
import com.trnd.trndapi.security.jwt.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
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
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied message here");
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto> handleMessageNotReadableException(HttpMessageNotReadableException ex){
        return ResponseEntity.badRequest().body(ResponseDto.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.toString())
                        .statusMsg(ex.getLocalizedMessage())
                        .data(SecurityUtils.hasRole(ERole.ROLE_ADMIN.name()) ? ex.getMostSpecificCause() : ex.getMessage())
                .build());
    }
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<String> handleConversionException(HttpMessageConversionException ex) {
        // Log the exception details and return an appropriate response
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data format");
    }

    @ExceptionHandler(MerchantNoFoundException.class)
    public ResponseEntity<String> handleMerchantNotFoundException(MerchantNoFoundException ex) {
        // Log the exception details and return an appropriate response
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Merchant not found");
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        // Log the exception details and return an appropriate response
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
