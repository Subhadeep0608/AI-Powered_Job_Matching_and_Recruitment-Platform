package com.deep.job_portal.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Entity @Table(name = "resumes") @Getter @Setter @NoArgsConstructor
public class Resume {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "candidate_id", nullable = false) private CandidateProfile candidate;
    @Column(name = "file_path", nullable = false, length = 500) private String filePath;
    @Lob @Column(name = "parsed_text") private String parsedText;
    @CreationTimestamp @Column(name = "uploaded_at", updatable = false) private Instant uploadedAt;
}
