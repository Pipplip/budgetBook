package de.phbeinternational.budgetbook.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import de.phbeinternational.budgetbook.entity.BudgetListItem;

public interface BudgetListItemRepository extends JpaRepository<BudgetListItem, Long>{
	
	// mit dem Import des JpaRepository wird automatisch CRUD verfuegbar gemacht
	
	// Benutzerdefinierte Methoden
	@Query(value = "SELECT * FROM budget_list_item WHERE user_id = :userId", nativeQuery = true)
	List<BudgetListItem> findByUser_Id(Long userId); // entspricht SELECT * FROM item WHERE user_id = ?; wenn man nicht @Query verwendet

	@Modifying
	@Transactional // wird bei INSERT, UPDATE und DELETE benoetigt
	@Query(value = "INSERT INTO budget_list_item (description, deposit, payout, added_date, user_id) VALUES (:description, :deposit, :payout, :added_date, :user_id)", nativeQuery = true)
	int saveItem(@Param("description") String description,
            @Param("deposit") Float deposit,
            @Param("payout") Float payout,
            @Param("added_date") LocalDate addedDate,
            @Param("user_id") Long userId);


}
