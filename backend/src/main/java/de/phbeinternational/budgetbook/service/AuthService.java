package de.phbeinternational.budgetbook.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.phbeinternational.budgetbook.dto.AuthenticationRequest;
import de.phbeinternational.budgetbook.dto.AuthenticationResponse;
import de.phbeinternational.budgetbook.dto.RegisterRequest;
import de.phbeinternational.budgetbook.entity.User;
import de.phbeinternational.budgetbook.exception.InvalidLoginException;
import de.phbeinternational.budgetbook.exception.LockedAccountException;
import de.phbeinternational.budgetbook.repository.UserRepository;

@Service
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}

	public AuthenticationResponse register(RegisterRequest request) {
		
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already registered");
		}
		
		var user = User.builder()
					.email(request.getEmail())
					.password(passwordEncoder.encode(request.getPassword()))
					.registrationDate(request.getRegistrationDate() != null? request.getRegistrationDate() : LocalDate.now())
					.userRole(request.getUserRole())
					.build()
					;
		userRepository.save(user);
		
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}
	
	public AuthenticationResponse login(AuthenticationRequest request) {
		var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new InvalidLoginException("Invalid login attempt"));
		
		// Überprüfen, ob der Benutzer gesperrt ist
		if(user.getBlockedUntil() != null && user.getBlockedUntil().isAfter(LocalDateTime.now())) {
			// Fehlversuche zuruecksetzen, damit er wieder drei Versuche hat, nachdem die Zeit abgelaufen ist.
			user.setFailedAttempts(0);
			userRepository.save(user); // Update die DB
			
	        throw new LockedAccountException(
	                "Your account is locked due to multiple failed login attempts. Please try again later",
	                user.getBlockedUntil()
	        );
		}
		
		try {
			// Erfolgreiche Anmeldung
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
	        // Fehlversuche zurücksetzen
	        user.setFailedAttempts(0);
	        user.setBlockedUntil(null);
	        userRepository.save(user); // Update die DB
	        
			var jwtToken = jwtService.generateToken(user);
			return AuthenticationResponse.builder().token(jwtToken).build();
			
		} catch(BadCredentialsException bce) {
	        user.setFailedAttempts(user.getFailedAttempts() + 1);
	        handleFailedLogin(user);  // Logik für den Umgang mit fehlgeschlagenen Anmeldungen
	        throw new InvalidLoginException("Invalid credentials.");
		} catch (LockedException e) {
	        // Konto ist gesperrt
	        user.setFailedAttempts(user.getFailedAttempts() + 1);
	        handleFailedLogin(user);  // Logik für den Umgang mit fehlgeschlagenen Anmeldungen
	        throw new LockedAccountException("Your account is locked due to multiple failed login attempts. Please try again later.", user.getBlockedUntil());
	    
	    } catch (AccountExpiredException e) {
	        // Konto abgelaufen
	        throw new InvalidLoginException("Your account has expired. Please contact support.");

	    } catch (Exception e) {
	        // Alle anderen Fehler, die nicht spezifisch sind
	        user.setFailedAttempts(user.getFailedAttempts() + 1);
	        handleFailedLogin(user);  // Logik für den Umgang mit fehlgeschlagenen Anmeldungen
	        throw new InvalidLoginException("An unexpected error occurred.");
	    }
	}
	
	private void handleFailedLogin(User user) {
	    // Wenn der Benutzer 3 Fehlversuche hatte, sperren wir ihn für 10 Minuten
	    if (user.getFailedAttempts() >= 3) {
	        user.setBlockedUntil(LocalDateTime.now().plusMinutes(10)); // Sperrzeit auf 10 Minuten setzen
	    }
	    userRepository.save(user);  // Update die DB
	}
}
