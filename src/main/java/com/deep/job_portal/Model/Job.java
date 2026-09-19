package com.deep.job_portal.Model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.deep.job_portal.Model.enums.EmploymentType;
import com.deep.job_portal.Model.enums.JobStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Entity @Table(name = "jobs") @Getter @Setter @NoArgsConstructor
public class Job {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "recruiter_id", nullable = false) private RecruiterProfile recruiter;
    @ManyToOne(optional = false) @JoinColumn(name = "company_id", nullable = false) private Company company;
    @Column(nullable = false) private String title;
    @Lob @Column(nullable = false) private String description;
    private String location;
    @Column(name = "salary_min", precision = 12, scale = 2) private BigDecimal salaryMin;
    @Column(name = "salary_max", precision = 12, scale = 2) private BigDecimal salaryMax;
    @Column(name = "experience_min") private Integer experienceMin;
    @Column(name = "experience_max") private Integer experienceMax;
    @Enumerated(EnumType.STRING) @Column(name = "employment_type", nullable = false) private EmploymentType employmentType = EmploymentType.FULL_TIME;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private JobStatus status = JobStatus.DRAFT;
    @CreationTimestamp @Column(name = "created_at", updatable = false) private Instant createdAt;
    @UpdateTimestamp @Column(name = "updated_at") private Instant updatedAt;
}
