package de.phbeinternational.budgetbook.exception;

import java.time.LocalDateTime;

public class LockedAccountException extends RuntimeException {
    private LocalDateTime blockedUntil;

    public LockedAccountException(String message, LocalDateTime blockedUntil) {
        super(message);
        this.blockedUntil = blockedUntil;
    }

    public LocalDateTime getBlockedUntil() {
        return blockedUntil;
    }

}
