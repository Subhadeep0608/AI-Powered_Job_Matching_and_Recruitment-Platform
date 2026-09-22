package com.deep.job_portal.Model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class CandidateSkillId implements Serializable {
    @Column(name = "candidate_id") private Long candidateId;
    @Column(name = "skill_id") private Long skillId;
}
