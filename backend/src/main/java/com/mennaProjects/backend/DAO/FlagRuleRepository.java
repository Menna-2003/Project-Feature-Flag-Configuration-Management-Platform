package com.mennaProjects.backend.DAO;

import com.mennaProjects.backend.Entities.FlagRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FlagRuleRepository extends JpaRepository<FlagRule, Long> {

    // Fetch rules for a specific flag environment configuration
    // ⚡ Critical: Must be ordered by ruleOrder to evaluate correctly (Top -> Down)
    // Using JOIN FETCH to grab the conditions immediately to prevent lazy loading errors
    @Query("SELECT fr FROM FlagRule fr " +
            "LEFT JOIN FETCH fr.conditions " +
            "WHERE fr.featureFlagEnvironment.id = :ffeId " +
            "ORDER BY fr.ruleOrder ASC")
    List<FlagRule> findRulesByFeatureFlagEnvId(@Param("ffeId") Long ffeId);
}