package de.phbeinternational.budgetbook.dto;

public class UserIdRequest {

    private Long userIdentifier;

    // Standard-Konstruktor
    public UserIdRequest() {}

    // Getter
    public Long getUserIdentifier() {
        return userIdentifier;
    }

    // Setter
    public void setUserIdentifier(Long userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    // Optional: Konstruktor für bequemere Erstellung
    public UserIdRequest(Long userIdentifier) {
        this.userIdentifier = userIdentifier;
    }
}
