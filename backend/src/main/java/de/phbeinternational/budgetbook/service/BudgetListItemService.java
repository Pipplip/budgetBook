package de.phbeinternational.budgetbook.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.phbeinternational.budgetbook.dto.BudgetListItemDTO;
import de.phbeinternational.budgetbook.entity.BudgetListItem;
import de.phbeinternational.budgetbook.repository.BudgetListItemRepository;

@Service
public class BudgetListItemService {

	// Mit diesem Object wird mit der Datenschicht kommuniziert
	private final BudgetListItemRepository budgetListItemRepository;
	
	@Autowired
	public BudgetListItemService(BudgetListItemRepository budgetListItemRepository) {
		this.budgetListItemRepository = budgetListItemRepository;
	}
	
	//**************
	// Zusaetzliche manuelle Methoden
	// **************
    public List<BudgetListItemDTO> getAllBudgetBookListItemsByUser(Long userId) {
//        return budgetListItemRepository.findByUser_Id(userId);
    	
        List<BudgetListItem> budgetListItems = budgetListItemRepository.findByUser_Id(userId);
        
        // Mappe die Items in ein DTO um, welches zurueck an den Client geht
        // kommt darauf was im DTO definiert ist.
        List<BudgetListItemDTO> budgetListItemDTOs = budgetListItems.stream()
                .map(budgetListItem -> new BudgetListItemDTO(
                        budgetListItem.getId(),
                        budgetListItem.getDeposit(),
                        budgetListItem.getPayout(),
                        budgetListItem.getDescription(),
                        budgetListItem.getAddedDate(),
                        budgetListItem.getUser().getId()  // Only include the user ID
                ))
                .collect(Collectors.toList());
        return budgetListItemDTOs;
    }
	
    public List<BudgetListItem> getAllBudgetListItems(){
    	return budgetListItemRepository.findAll();
    }
    
    public int saveBudgetListItem(BudgetListItem item, Long userId) {
//    	return budgetListItemRepository.save(item);
    	return budgetListItemRepository.saveItem(item.getDescription(), item.getDeposit(), item.getPayout(), item.getAddedDate(), userId);
    }
	
    public void deleteBudgetListItem(Long id) {
    	budgetListItemRepository.deleteById(id);
    }
    
	public Optional<BudgetListItem> getBudgetListItemById(Long id) {
		return budgetListItemRepository.findById(id);
	}
    
}
