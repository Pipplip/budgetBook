package de.phbeinternational.budgetbook.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import de.phbeinternational.budgetbook.entity.User;
import de.phbeinternational.budgetbook.service.UserService;

@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
@RestController
@RequestMapping("/api/user")
@Deprecated
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	// http://localhost:8080/budgetbook-1.0/api/user/health
    @GetMapping(value = "/health", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> sayHello() {
    	String text = "Hello, JAX-RS! I'm healthy.";
    	return ResponseEntity.ok().body(text);
    }
	
	// GET
	@GetMapping
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	// http://localhost:8080/budgetbook-1.0/api/user/1
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id){
		Optional<User> user = userService.getUserById(id);
		if(user.isPresent()) {
			return ResponseEntity.ok(user.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createUser(@RequestBody User user) {
		try {
			
	        // Passwort hashen, bevor der Benutzer gespeichert wird
//	        String hashedPassword = userService.hashPassword(user.getPassword());
//	        user.setPassword(hashedPassword);
			
			userService.saveUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
		}
		catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
		Optional<User> tmpUser = userService.getUserById(id);
		if(tmpUser.isPresent()) {
			User updateUser = tmpUser.get();
			updateUser.setEmail(user.getEmail());
			
	        // Passwort hashen, bevor der Benutzer gespeichert wird
//	        String hashedPassword = userService.hashPassword(user.getPassword());
//	        user.setPassword(hashedPassword);
			
			updateUser.setPassword(user.getPassword());
			
			return ResponseEntity.ok(userService.updateUser(updateUser));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	
}
