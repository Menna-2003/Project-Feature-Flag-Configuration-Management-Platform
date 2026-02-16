package com.mennaProjects.backend.DAO;

import com.mennaProjects.backend.Entities.FeatureFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureFlagRepository extends JpaRepository<FeatureFlag, Long> {

    // Check for duplicates when creating new flags
    boolean existsByFlagKey(String flagKey);
}