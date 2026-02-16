package com.mennaProjects.backend.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "flag_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlagRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "feature_flag_env_id")
    @ToString.Exclude
    private FeatureFlagEnvironment featureFlagEnvironment;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type", nullable = false)
    private RuleType ruleType;

    @Column(name = "rule_order", nullable = false)
    private Integer ruleOrder;

    @Column(name = "percentage_value")
    private Integer percentageValue;

    @OneToMany(mappedBy = "flagRule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RuleCondition> conditions;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}