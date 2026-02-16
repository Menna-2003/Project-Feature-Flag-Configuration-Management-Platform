package com.mennaProjects.backend.DAO;

import com.mennaProjects.backend.Entities.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Long> {

    Optional<Environment> findByName(String name);
}