package com.deep.job_portal.Model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class SavedJobId implements Serializable {
    @Column(name = "candidate_id") private Long candidateId;
    @Column(name = "job_id") private Long jobId;
}
