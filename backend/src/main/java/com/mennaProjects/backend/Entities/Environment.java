package com.mennaProjects.backend.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "environments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Environment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "is_protected")
    private boolean isProtected;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}