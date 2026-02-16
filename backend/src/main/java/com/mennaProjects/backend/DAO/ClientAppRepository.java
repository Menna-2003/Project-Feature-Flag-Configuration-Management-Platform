package com.mennaProjects.backend.DAO;

import com.mennaProjects.backend.Entities.ClientApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClientAppRepository extends JpaRepository<ClientApp, Long> {

    // ⚡ Performance critical: This is hit on every API request
    // Spring Data caches the query plan, but ensure 'api_key' has an index in DB
    Optional<ClientApp> findByApiKey(String apiKey);
}