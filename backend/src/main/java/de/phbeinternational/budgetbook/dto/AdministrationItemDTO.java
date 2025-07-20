package de.phbeinternational.budgetbook.dto;

import java.time.LocalDate;

import de.phbeinternational.budgetbook.entity.UserRole;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AdministrationItemDTO {
	
	// Dieses DTO liefert die Daten zum Client fuer die Administrationsansicht
	
	private LocalDate registrationDate;
	private Long id;
    private String email;
    private UserRole userRole = UserRole.USER;
    
	public AdministrationItemDTO(LocalDate registrationDate, Long id, String email, UserRole userRole) {
		this.registrationDate = registrationDate;
		this.id = id;
		this.email = email;
		this.userRole = userRole;
	}
	
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
    
    
}
