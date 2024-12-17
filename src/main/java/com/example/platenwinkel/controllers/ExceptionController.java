package com.example.platenwinkel.controllers;

import com.example.platenwinkel.exceptions.BadRequestException;
import com.example.platenwinkel.exceptions.DuplicateRecordException;
import com.example.platenwinkel.exceptions.InvalidInputException;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionController {
    public static class ErrorResponse {
        private String error;
        private String message;

        public ErrorResponse(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse("Not Found", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse("Not Found", "User not found: " + exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException exception) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid Input", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception exception) {
        exception.printStackTrace(); // Consider logging instead of printing in production
        return buildErrorResponse("Internal Server Error", "An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateRecordException(DuplicateRecordException exception) {
        ErrorResponse errorResponse = new ErrorResponse("Conflict", exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException exception) {
        ErrorResponse errorResponse = new ErrorResponse("Unauthorized", "Incorrect username or password");
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }


    private ResponseEntity<ErrorResponse> buildErrorResponse(String error, String message, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(error, message);
        return new ResponseEntity<>(errorResponse, status);
    }
}

