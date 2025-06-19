package com.restoreit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.restoreit.models.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    @Query("SELECT DISTINCT u FROM User u JOIN u.products p WHERE p.id = :productId AND p.user.id = u.id")
    User findByProductId(UUID productId);

    boolean existsByEmail(String email);
}
