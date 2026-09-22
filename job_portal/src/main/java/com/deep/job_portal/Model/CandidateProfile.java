package com.deep.job_portal.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "candidate_profiles") @Getter @Setter @NoArgsConstructor
public class CandidateProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @OneToOne(optional = false) @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;
    private String headline;
    @Lob private String bio;
    private String location;
    @Lob private String experience;
    @Lob private String education;
    @Column(name = "github_url", length = 500) private String githubUrl;
    @Column(name = "linkedin_url", length = 500) private String linkedinUrl;
    @Column(name = "portfolio_url", length = 500) private String portfolioUrl;
}
