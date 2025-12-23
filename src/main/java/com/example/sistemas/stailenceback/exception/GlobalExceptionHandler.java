package com.example.sistemas.stailenceback.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, WebRequest req) {
        ErrorResponse err = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path(req.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex, WebRequest req) {
        ErrorResponse err = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message(ex.getMessage())
                .path(req.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex, WebRequest req) {
        ErrorResponse err = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Unauthorized")
                .message(ex.getMessage())
                .path(req.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbidden(ForbiddenException ex, WebRequest req) {
        ErrorResponse err = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .error("Forbidden")
                .message(ex.getMessage())
                .path(req.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, WebRequest req) {
        String details = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse err = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .error("Validation Error")
                .message(details)
                .path(req.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleConstraint(ConstraintViolationException ex, WebRequest req) {
        String details = ex.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse err = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Error")
                .message(details)
                .path(req.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwt(JwtException ex, WebRequest req) {
        ErrorResponse err = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error("Invalid Token")
                .message(ex.getMessage())
                .path(req.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex, WebRequest req) {
        ErrorResponse err = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Data Integrity Violation")
                .message(ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage())
                .path(req.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(err, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception ex, WebRequest req) {
        ErrorResponse err = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message(ex.getMessage())
                .path(req.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
