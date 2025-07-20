package de.phbeinternational.budgetbook.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.phbeinternational.budgetbook.dto.BudgetListItemDTO;
import de.phbeinternational.budgetbook.dto.UserIdRequest;
import de.phbeinternational.budgetbook.entity.BudgetListItem;
import de.phbeinternational.budgetbook.entity.User;
import de.phbeinternational.budgetbook.service.BudgetListItemService;

//@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
@RestController
@RequestMapping("/api/budgetlistItem")
public class BudgetListController {

	private final BudgetListItemService budgetListItemService;
	
	public BudgetListController(BudgetListItemService budgetListItemService) {
		this.budgetListItemService = budgetListItemService;
	}
	
	@GetMapping
	public List<BudgetListItem> getAllBudgetListItems(){
		return budgetListItemService.getAllBudgetListItems();
	}
	
	@PostMapping("/getitemsbyuser")
	public ResponseEntity<List<BudgetListItemDTO>> getAllBudgetListItemsByUserId(@RequestBody UserIdRequest userId) {
		//@RequestBody UserIdRequest userId
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return ResponseEntity.status(401).body(null);  // User ist nicht authentifiziert
		}

		User user = (User) authentication.getPrincipal();
		Long userIdFromToken = user.getId();

        // Vergleiche den userId im Body mit dem userId aus dem Token
        if (!Objects.equals(userIdFromToken, userId.getUserIdentifier())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();  // Zugriff verweigert
        }


//		Optional<List<BudgetListItem>> budgetList = budgetListItemService.getAllBudgetBookListItemsByUser(userIdFromToken);
		List<BudgetListItemDTO> budgetList = budgetListItemService.getAllBudgetBookListItemsByUser(userId.getUserIdentifier());
		if(budgetList != null && !budgetList.isEmpty()) {
			return ResponseEntity.ok(budgetList);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<BudgetListItem> createItem(@RequestBody BudgetListItem item){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return ResponseEntity.status(401).body(null);  // User ist nicht authentifiziert
		}
		User user = (User) authentication.getPrincipal();
		Long userIdFromToken = user.getId();
		
		int returnValue = budgetListItemService.saveBudgetListItem(item, userIdFromToken);
		
		if(returnValue == 1) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBudgetListItem(@PathVariable Long id){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return ResponseEntity.status(401).body(null);  // User ist nicht authentifiziert
		}
		
		budgetListItemService.deleteBudgetListItem(id);
		return ResponseEntity.noContent().build();
	}
	
	
//	@GetMapping("/{userId}")
//	public ResponseEntity<List<BudgetListItem>> getAllBudgetListItemsByUserId(@PathVariable Long userId){
//		Optional<List<BudgetListItem>> budgetList = budgetListItemService.getAllBudgetBookListItemsByUser(userId);
//		if(!budgetList.isEmpty()) {
//			return ResponseEntity.ok(budgetList.get());
//		}else {
//			return ResponseEntity.notFound().build();
//		}
//	}
//	
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Void> deleteBudgetListItem(@PathVariable Long id){
//		budgetListItemService.deleteBudgetListItem(id);
//		return ResponseEntity.noContent().build();
//	}
//	
//	@PutMapping("/{id}")
//	public ResponseEntity<BudgetListItem> updateBudgetListItem(@PathVariable Long id, @RequestBody BudgetListItem item){
//		Optional<BudgetListItem> listOptional = budgetListItemService.getBudgetListItemById(id);
//		if(listOptional.isPresent()) {
//			BudgetListItem updateItem = listOptional.get();
//			updateItem.setDeposit(item.getDeposit());
//			updateItem.setDescription(item.getDescription());
//			updateItem.setPayout(item.getPayout());
//			updateItem.setAddedDate(item.getAddedDate());
//			
//			return ResponseEntity.ok(budgetListItemService.saveBudgetListItem(updateItem));
//		}else {
//			return ResponseEntity.notFound().build();
//		}
//	}
	
}
