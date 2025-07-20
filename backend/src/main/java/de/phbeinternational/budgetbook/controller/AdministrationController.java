package de.phbeinternational.budgetbook.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.phbeinternational.budgetbook.dto.AdministrationItemDTO;
import de.phbeinternational.budgetbook.entity.User;
import de.phbeinternational.budgetbook.entity.UserRole;
import de.phbeinternational.budgetbook.service.AdministrationService;

@RestController
@RequestMapping("/api/administration")
public class AdministrationController {
	
	private final AdministrationService administrationService;

	public AdministrationController(AdministrationService administrationService) {
		this.administrationService = administrationService;
	}
	
	@PostMapping("/getuserlist")
	public ResponseEntity<List<AdministrationItemDTO>> getUserList(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return ResponseEntity.status(401).body(null);  // User ist nicht authentifiziert
		}
		
		// pruefe ob der Client Admin-Rolle hat
		User user = (User) authentication.getPrincipal();
		if(user.getUserRole() != UserRole.ADMIN) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		List<AdministrationItemDTO> userList = administrationService.getUserList();
		if(userList != null && !userList.isEmpty()) {
			return ResponseEntity.ok(userList);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return ResponseEntity.status(401).body(null);  // User ist nicht authentifiziert
		}
		
		administrationService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
}
