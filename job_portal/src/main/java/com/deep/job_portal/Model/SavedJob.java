package com.deep.job_portal.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "saved_jobs") @Getter @Setter @NoArgsConstructor
public class SavedJob {
    @EmbeddedId private SavedJobId id;
    @MapsId("candidateId") @ManyToOne(optional = false) @JoinColumn(name = "candidate_id") private CandidateProfile candidate;
    @MapsId("jobId") @ManyToOne(optional = false) @JoinColumn(name = "job_id") private Job job;
}
