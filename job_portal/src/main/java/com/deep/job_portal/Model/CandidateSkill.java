package com.deep.job_portal.Model;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

import com.deep.job_portal.Model.enums.Proficiency;

@Entity @Table(name = "candidate_skills") @Getter @Setter @NoArgsConstructor
public class CandidateSkill {
    @EmbeddedId private CandidateSkillId id;
    @MapsId("candidateId") @ManyToOne(optional = false) @JoinColumn(name = "candidate_id") private CandidateProfile candidate;
    @MapsId("skillId") @ManyToOne(optional = false) @JoinColumn(name = "skill_id") private Skill skill;
    @Column(precision = 4, scale = 1) private BigDecimal experience;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private Proficiency proficiency = Proficiency.BEGINNER;
}