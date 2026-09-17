package com.example.user_service.exception;

import com.example.user_service.dto.commonRes.ErrorResponse;
import com.example.user_service.dto.commonRes.ValidationResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new ValidationResponse(false , "Bad Request" , LocalDateTime.now() ,HttpStatus.BAD_REQUEST.value(), request.getRequestURI() , errors));
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

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorResponse> NotFoundException(NotFoundException  exception , HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new ErrorResponse(false , exception.getMessage() , LocalDateTime.now() ,HttpStatus.NOT_FOUND.value(), request.getRequestURI()));
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

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRunTimeException(RuntimeException exception , HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new ErrorResponse(false , "Internal Server Error" , LocalDateTime.now() ,HttpStatus.INTERNAL_SERVER_ERROR.value(), request.getRequestURI()));
    }
}
