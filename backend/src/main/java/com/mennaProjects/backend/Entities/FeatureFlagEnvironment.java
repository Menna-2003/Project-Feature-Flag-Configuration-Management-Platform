package com.mennaProjects.backend.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feature_flag_environments",
        uniqueConstraints = @UniqueConstraint(columnNames = {"feature_flag_id", "environment_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeatureFlagEnvironment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "feature_flag_id")
    private FeatureFlag featureFlag;

    @ManyToOne(optional = false)
    @JoinColumn(name = "environment_id")
    private Environment environment;

    private boolean enabled;

    @Column(name = "default_value")
    private boolean defaultValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_modified_by")
    @ToString.Exclude
    private User lastModifiedBy;

    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;
}