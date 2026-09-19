package com.deep.job_portal.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity @Table(name = "application_status_history") @Getter @Setter @NoArgsConstructor
public class ApplicationStatusHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "application_id", nullable = false) private Application application;
    @Column(name = "old_status", length = 30) private String oldStatus;
    @Column(name = "new_status", nullable = false, length = 30) private String newStatus;
    @CreationTimestamp @Column(name = "changed_at", updatable = false) private Instant changedAt;
    @ManyToOne @JoinColumn(name = "changed_by") private User changedBy;
}
