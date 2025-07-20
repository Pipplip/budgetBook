package de.phbeinternational.budgetbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	// wurde anglegt, damit man im AuthService login, die richtigen Fehler wirft

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<Object> handleInvalidCredentialsException(InvalidLoginException ex) {
        return new ResponseEntity<>(new ErrorResponse("Invalid credentials", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LockedAccountException.class)
    public ResponseEntity<Object> handleLockedAccountException(LockedAccountException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage(), "Please try again later."), HttpStatus.LOCKED);
    }

    // Allgemeine Fehlerbehandlung
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(new ErrorResponse("Internal Server Error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
