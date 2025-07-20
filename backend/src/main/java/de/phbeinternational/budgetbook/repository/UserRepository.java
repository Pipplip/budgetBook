package de.phbeinternational.budgetbook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import de.phbeinternational.budgetbook.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	// mit dem Import des JpaRepository wird automatisch CRUD verfuegbar gemacht
	
    // Method to check if the email already exists in the database
    boolean existsByEmail(String email);
    
    Optional<User> findByEmail(String email);
    
    // Diese Methode ist fuer die Administration vorgesehen,
    // damit der Admin-client alle User bekommt
    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> getUserList();
}
