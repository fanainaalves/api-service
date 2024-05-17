package com.ms.service.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationException {

    @ExceptionHandler
    public ResponseEntity treatError400(MethodArgumentNotValidException e) {
        var error = e.getFieldErrors();
        return ResponseEntity.badRequest().body(error.stream().map(ValidationErrorDTO::new).toList());
    }
    private record ValidationErrorDTO(String field, String message){
        public ValidationErrorDTO(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }

}
