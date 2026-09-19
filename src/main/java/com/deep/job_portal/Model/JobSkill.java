package com.deep.job_portal.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "job_skills") @Getter @Setter @NoArgsConstructor
public class JobSkill {
    @EmbeddedId private JobSkillId id;
    @MapsId("jobId") @ManyToOne(optional = false) @JoinColumn(name = "job_id") private Job job;
    @MapsId("skillId") @ManyToOne(optional = false) @JoinColumn(name = "skill_id") private Skill skill;
    @Column(nullable = false) private boolean required = true;
    @Column(nullable = false) private Integer importance = 3;
}
