package de.phbeinternational.budgetbook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.phbeinternational.budgetbook.entity.User;
import de.phbeinternational.budgetbook.repository.UserRepository;

@Service
@Deprecated
public class UserService {
	

	// Mit diesem Object wird mit der Datenschicht kommuniziert
	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	//**************
	// Zusaetzliche manuelle Methoden
	// **************
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}
	
	public User saveUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
        	// Email address already in use!
            throw new IllegalArgumentException("Cannot save user");
        }
		
		return userRepository.save(user);
	}
	
	public User updateUser(User user) {
		return userRepository.save(user);
	}
	
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
	
}
