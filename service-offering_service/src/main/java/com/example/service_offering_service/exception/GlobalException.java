package com.example.service_offering_service.exception;


import com.example.service_offering_service.dto.commonRes.ErrorResponse;
import com.example.service_offering_service.dto.commonRes.ValidationResponse;
import com.example.service_offering_service.exception.ForbiddenException;
import com.example.service_offering_service.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import tools.jackson.databind.exc.UnrecognizedPropertyException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationResponse> validationException(MethodArgumentNotValidException exception , HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

   exception.getBindingResult().getFieldErrors().forEach((fieldError) -> {
       errors.put(fieldError.getField(), fieldError.getDefaultMessage());
   });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new ValidationResponse(false , "Bad Request" , LocalDateTime.now() ,HttpStatus.BAD_REQUEST.value(), request.getRequestURI() , errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ValidationResponse> handleMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        Map<String, String> fieldErrors = new HashMap<>();
        Throwable cause = exception.getCause();

        if (cause instanceof UnrecognizedPropertyException ex) {
            String fieldName = ex.getPropertyName();
            fieldErrors.put(fieldName, "Unknown field");
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ValidationResponse(
                        false,
                        "Invalid request body",
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getRequestURI(),
                        fieldErrors
                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ValidationResponse> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request
    ) {

        Map<String, String> fieldErrors = new HashMap<>();

        String fieldName = exception.getName();

        fieldErrors.put(
                fieldName,
                "Invalid value. Expected type: " +
                        exception.getRequiredType().getSimpleName()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ValidationResponse(
                        false,
                        "Invalid request parameter",
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getRequestURI(),
                        fieldErrors
                ));
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> NotFoundException(NotFoundException  exception , HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ErrorResponse(false , exception.getMessage() , LocalDateTime.now() ,HttpStatus.NOT_FOUND.value(), request.getRequestURI()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(DuplicateKeyException exception , HttpServletRequest request ) {
        return new ResponseEntity<>(new ErrorResponse(false , exception.getMessage() , LocalDateTime.now() , HttpStatus.CONFLICT.value() , request.getRequestURI()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception , HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new ErrorResponse(false , "Something went wrong , Please try after some time" , LocalDateTime.now() ,HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(
            NoResourceFoundException exception,
            HttpServletRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        false,
                        "Endpoint not found",
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(
            ForbiddenException exception,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.FORBIDDEN;

        return ResponseEntity.status(status)
                .body(new ErrorResponse(
                        false,
                        exception.getMessage(),
                        LocalDateTime.now(),
                        status.value(),
                        request.getRequestURI()
                ));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRunTimeException(RuntimeException exception , HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new ErrorResponse(false , "Internal Server Error" , LocalDateTime.now() ,HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI()));
    }
}
