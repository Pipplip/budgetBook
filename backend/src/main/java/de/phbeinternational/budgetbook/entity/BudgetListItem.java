package de.phbeinternational.budgetbook.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@Entity
//@Getter // lombok
//@Setter // lombok
@NoArgsConstructor // lombok
public class BudgetListItem {

    // ======================================
    // =             Attributes             =
    // ======================================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private float deposit; // Einzahlung
    private float payout; // Auszahlung
    
    private String description;
    @Column(name = "added_date")
    private LocalDate addedDate;
     
    // Unidirektionale Beziehung: User weiss nichts von BudgetListItems
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    // ======================================
    // =             Getter/Setter          =
    // ======================================
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
    // ======================================
    // =   Methods hash, equals, toString   =
    // ======================================
	@Override
    public String toString() {
        return "BudgetList{" +
            "id=" + id +
            ", deposit='" + deposit + '\'' +
            ", payout='" + payout + '\'' +
            ", description=" + description +
            ", addedDate=" + addedDate +
            ", user=" + user +
            '}';
    }

}
