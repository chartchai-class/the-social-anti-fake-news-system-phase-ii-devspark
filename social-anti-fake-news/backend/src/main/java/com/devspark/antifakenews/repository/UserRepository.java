package com.devspark.antifakenews.repository;

import com.devspark.antifakenews.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity
 * 
 * @author DevSpark Team
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username
     * 
     * @param username the username
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email
     * 
     * @param email the email
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if username exists
     * 
     * @param username the username
     * @return true if username exists
     */
    boolean existsByUsername(String username);

    /**
     * Check if email exists
     * 
     * @param email the email
     * @return true if email exists
     */
    boolean existsByEmail(String email);

    /**
     * Find active users
     * 
     * @return list of active users
     */
    @Query("SELECT u FROM User u WHERE u.isActive = true")
    java.util.List<User> findActiveUsers();

    /**
     * Find users by role
     * 
     * @param role the role
     * @return list of users with the specified role
     */
    @Query("SELECT u FROM User u WHERE u.role = :role")
    java.util.List<User> findByRole(@Param("role") User.Role role);
}
