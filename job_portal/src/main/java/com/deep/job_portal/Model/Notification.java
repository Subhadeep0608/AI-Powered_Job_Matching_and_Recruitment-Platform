package com.deep.job_portal.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity @Table(name = "notifications") @Getter @Setter @NoArgsConstructor
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "user_id", nullable = false) private User user;
    @Column(nullable = false, length = 100) private String type;
    @Lob private String payload; // Store JSON text; use JsonNode with a Hibernate JSON type if preferred.
    @Column(name = "read", nullable = false) private boolean isRead = false;
    @CreationTimestamp @Column(name = "created_at", updatable = false) private Instant createdAt;
}

