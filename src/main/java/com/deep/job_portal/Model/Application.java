package com.deep.job_portal.Model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.deep.job_portal.Model.enums.ApplicationStatus;

import java.time.Instant;

@Entity @Table(name = "applications", uniqueConstraints = @UniqueConstraint(columnNames = {"job_id", "candidate_id"})) @Getter @Setter @NoArgsConstructor
public class Application {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "job_id", nullable = false) private Job job;
    @ManyToOne(optional = false) @JoinColumn(name = "candidate_id", nullable = false) private CandidateProfile candidate;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private ApplicationStatus status = ApplicationStatus.APPLIED;
    @CreationTimestamp @Column(name = "applied_at", updatable = false) private Instant appliedAt;
}
