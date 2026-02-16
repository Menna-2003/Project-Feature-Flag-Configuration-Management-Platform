package com.mennaProjects.backend.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "client_apps")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientApp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "api_key", nullable = false, unique = true)
    private String apiKey;

    @ManyToOne(optional = false)
    @JoinColumn(name = "environment_id")
    private Environment environment;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}