package de.phbeinternational.budgetbook.exception;

public class InvalidLoginException extends RuntimeException {
    
    // Standard-Konstruktor
    public InvalidLoginException() {
        super("Invalid login attempt");
    }

    // Konstruktor mit einer benutzerdefinierten Fehlermeldung
    public InvalidLoginException(String message) {
        super(message);
    }
    
    // Konstruktor mit einer benutzerdefinierten Fehlermeldung und einem Throwable (ursprüngliche Ursache)
    public InvalidLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
