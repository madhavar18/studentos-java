package com.studentos.repository;

import com.studentos.model.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // JpaRepository<User, Integer>:
    // User = the entity type this repository manages
    // Integer = the type of the primary key (id field type)

    // Spring Data Jpa provides these methods automatically - no implementation needed:
    // findAll()           → SELECT * FROM users
    // findById(id)        → SELECT * FROM users WHERE id = ?
    // save(user)          → INSERT or UPDATE (INSERT if no id, UPDATE if id exists)
    // deleteById(id)      → DELETE FROM users WHERE id = ?
    // count()             → SELECT COUNT(*) FROM users
    // existsById(id)      → SELECT EXISTS(SELECT 1 FROM users WHERE id = ?)

    Optional<User> findByUserName(String userName);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);
}
