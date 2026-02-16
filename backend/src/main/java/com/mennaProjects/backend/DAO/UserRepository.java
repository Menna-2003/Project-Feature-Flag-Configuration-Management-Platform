package com.mennaProjects.backend.DAO;

import com.mennaProjects.backend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // For login
    Optional<User> findByEmail(String email);

    // For checking if an email is taken during registration
    boolean existsByEmail(String email);
}
