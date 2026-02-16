package com.mennaProjects.backend.DAO;

import com.mennaProjects.backend.Entities.FeatureFlagEnvironment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeatureFlagEnvironmentRepository extends JpaRepository<FeatureFlagEnvironment, Long> {

    // 1. Fetch a specific flag for an environment (e.g., checking just "new-checkout")
    @Query("SELECT ffe FROM FeatureFlagEnvironment ffe " +
            "JOIN FETCH ffe.featureFlag ff " +
            "WHERE ff.flagKey = :flagKey AND ffe.environment.id = :envId")
    Optional<FeatureFlagEnvironment> findByFlagKeyAndEnvId(@Param("flagKey") String flagKey,
                                                           @Param("envId") Long envId);

    // 2. Fetch ALL flags for an environment (for initializing the SDK)
    // We use JOIN FETCH to avoid N+1 queries (loading the flag details in one go)
    @Query("SELECT ffe FROM FeatureFlagEnvironment ffe " +
            "JOIN FETCH ffe.featureFlag " +
            "WHERE ffe.environment.id = :envId")
    List<FeatureFlagEnvironment> findAllByEnvironmentId(@Param("envId") Long envId);
}