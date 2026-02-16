package com.mennaProjects.backend.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feature_flags")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureFlag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flag_key", nullable = false, unique = true)
    private String flagKey;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "flag_type", nullable = false)
    private FlagType flagType;

    private boolean archived;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    @ToString.Exclude
    private User createdBy;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}