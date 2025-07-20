package de.phbeinternational.budgetbook.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.phbeinternational.budgetbook.dto.AuthenticationRequest;
import de.phbeinternational.budgetbook.dto.AuthenticationResponse;
import de.phbeinternational.budgetbook.dto.RegisterRequest;
import de.phbeinternational.budgetbook.service.AuthService;

//@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*") // Also defined globally in WebConfig class
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService service;
	
	public AuthController(AuthService service) {
		this.service = service;
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
		try {
			return ResponseEntity.ok(service.register(request));
		} catch (Exception e) {
	        AuthenticationResponse errorResponse = new AuthenticationResponse("Registration failed: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(service.login(request));
	}
}
