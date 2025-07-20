package de.phbeinternational.budgetbook.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
//@AllArgsConstructor
@Table(name = "users")
//@Getter // lombok
//@Setter // lombok
public class User implements UserDetails{

    // ======================================
    // =             Attributes             =
    // ======================================
	
	// User weiss nichts von BudgetListItems = Unidirektionale Beziehung

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)// Email darf es nur einmal in der DB geben
    private String email;
    private String password;

    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;
    
    private LocalDateTime blockedUntil;
    private int failedAttempts; // Anzahl der fehlgeschlagenen Versuche
    
    public User() {
    	this.registrationDate = LocalDate.now();
    }
    
    public User(String email, String password, LocalDate registrationDate, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.userRole = userRole;
    }
    
    // ======================================
    // =             Getter/Setter          =
    // ======================================
    
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
	
	public LocalDateTime getBlockedUntil() {
		return blockedUntil;
	}

	public void setBlockedUntil(LocalDateTime blockedUntil) {
		this.blockedUntil = blockedUntil;
	}
	
	public int getFailedAttempts() {
		return failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		this.failedAttempts = failedAttempts;
	}

	// ======================================
    // =             Builder          =
    // ======================================
    public static class UserBuilder {
        private String email;
        private String password;
        private LocalDate registrationDate;
        private UserRole userRole = UserRole.USER;

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }
        public UserBuilder password(String password) {
        	this.password = password;
        	return this;
        }
        public UserBuilder registrationDate(LocalDate registrationDate) {
        	this.registrationDate = registrationDate;
        	return this;
        }
        public UserBuilder userRole(UserRole userRole) {
        	this.userRole = userRole;
        	return this;
        }

        public User build() {
            return new User(email, password, registrationDate, userRole);
        }
    }
    
    public static UserBuilder builder() {
        return new UserBuilder();
    }
    
    // ======================================
    // =   Methods hash, equals, toString   =
    // ======================================
    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", registrationDate=" + registrationDate +
            ", userRole=" + userRole +
            '}';
    }
    
    // ======================================
    // = Spring UserDetails implementations =
    // ======================================
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(userRole.name()));
	}

	@Override
	public String getUsername() {
		return email;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		//return UserDetails.super.isAccountNonExpired();
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		//return UserDetails.super.isAccountNonLocked();
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		//return UserDetails.super.isCredentialsNonExpired();
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		//return UserDetails.super.isEnabled();
		return true;
	}
}
