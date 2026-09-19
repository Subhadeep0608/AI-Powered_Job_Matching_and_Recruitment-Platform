package com.deep.job_portal.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "recruiter_profiles") @Getter @Setter @NoArgsConstructor
public class RecruiterProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToOne(optional = false) @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;
    @ManyToOne(optional = false) @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    private String designation;
}
