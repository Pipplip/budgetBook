package de.phbeinternational.budgetbook.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.phbeinternational.budgetbook.dto.AdministrationItemDTO;
import de.phbeinternational.budgetbook.entity.User;
import de.phbeinternational.budgetbook.repository.UserRepository;

@Service
public class AdministrationService {
	
	// Dieser Service ist nur dafuer da, dass ein ADMIN clientseitig
	// Aenderungen ueber User machen kann
	
	// Mit diesem Object wird mit der Datenschicht kommuniziert
	// Man braucht fuer die Administration nicht extra einen eigene Repository
	// um auf User zuzugreifen
	private final UserRepository userRepository;
	
	@Autowired
	public AdministrationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<AdministrationItemDTO> getUserList(){
		List<User> users = userRepository.getUserList();
		// Mappe die Items in ein DTO um, welches zurueck an den Client geht
        // kommt darauf was im DTO definiert ist.
        List<AdministrationItemDTO> userDTO = users.stream()
                .map(user -> new AdministrationItemDTO(
                		user.getRegistrationDate(),
                		user.getId(),
                		user.getEmail(),
                		user.getUserRole()
                ))
                .collect(Collectors.toList());
        return userDTO;
	}
	
    public void deleteUser(Long id) {
    	userRepository.deleteById(id);
    }

}
