package com.deep.job_portal.Model;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable @Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class JobSkillId implements Serializable {
    @Column(name = "job_id") private Long jobId;
    @Column(name = "skill_id") private Long skillId;
}
