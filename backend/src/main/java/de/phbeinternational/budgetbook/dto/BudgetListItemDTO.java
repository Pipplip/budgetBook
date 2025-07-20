package de.phbeinternational.budgetbook.dto;

import java.time.LocalDate;

public class BudgetListItemDTO {

    private Long id;
    private float deposit;
    private float payout;
    private String description;
    private LocalDate addedDate;
    private Long userId; // Only the user's ID

    // Constructors
    public BudgetListItemDTO(Long id, float deposit, float payout, String description, LocalDate addedDate, Long userId) {
        this.id = id;
        this.deposit = deposit;
        this.payout = payout;
        this.description = description;
        this.addedDate = addedDate;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public float getPayout() {
        return payout;
    }

    public void setPayout(float payout) {
        this.payout = payout;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
