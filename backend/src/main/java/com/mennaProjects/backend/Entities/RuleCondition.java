package com.mennaProjects.backend.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rule_conditions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "rule_id")
    @ToString.Exclude
    private FlagRule flagRule;

    @Column(name = "condition_key", nullable = false)
    private String conditionKey;

    @Column(name = "condition_value", nullable = false)
    private String conditionValue;
}