package com.mennaProjects.backend.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "full_name")
    private String fullName;

    @Builder.Default
    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // ✅ CORRECT: Now 'Role' refers to the Enum, not an Entity
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}