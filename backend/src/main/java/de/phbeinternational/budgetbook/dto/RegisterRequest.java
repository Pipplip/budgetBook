package de.phbeinternational.budgetbook.dto;

import java.time.LocalDate;

import de.phbeinternational.budgetbook.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
	
	// ist für die Datenerfassung von der API-Seite (Request Body) und muss parallel zur User Entitiy bestehen,
	// da sich die Entity nur um die Datenbank Einträge kümmert
	
    private String email;
    private String password;
    private LocalDate registrationDate;
    private UserRole userRole = UserRole.USER;
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDate getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
    
    
}
