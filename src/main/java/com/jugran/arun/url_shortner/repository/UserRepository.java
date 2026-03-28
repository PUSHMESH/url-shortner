package com.jugran.arun.url_shortner.repository;

import com.jugran.arun.url_shortner.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by API development key
     */
    Optional<User> findByApiDevKey(String apiDevKey);
    
    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if user exists by API dev key
     */
    boolean existsByApiDevKey(String apiDevKey);
}

