package org.gerasic.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpected(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder("Validation failed:\n");
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.append("- ").append(error.getField())
                    .append(": ").append(error.getDefaultMessage()).append("\n");
        }
        return ResponseEntity.badRequest().body(errors.toString());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
//    @ExceptionHandler(AccountAlreadyExistsException.class)
//    public ResponseEntity<String> handleAccountAlreadyExists(AccountAlreadyExistsException ex) {
//        return ResponseEntity.status(HttpStatus.CONFLICT).body("Account already exists");
//    }
//
//    @ExceptionHandler(AccountNotFoundException.class)
//    public ResponseEntity<String> handleAccountNotFound(AccountNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
//    }
//
//    @ExceptionHandler(InsufficientFundsException.class)
//    public ResponseEntity<String> handleInsufficientFunds(InsufficientFundsException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds");
//    }
//
//    @ExceptionHandler(InvalidTransferException.class)
//    public ResponseEntity<String> handleInvalidTransfer(InvalidTransferException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid transfer");
//    }
//
//    @ExceptionHandler(SameLoginException.class)
//    public ResponseEntity<String> handleSameLogin(SameLoginException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot add yourself as a friend");
//    }
//
//    @ExceptionHandler(UserAlreadyExistsException.class)
//    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
//        return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
//    }
}
