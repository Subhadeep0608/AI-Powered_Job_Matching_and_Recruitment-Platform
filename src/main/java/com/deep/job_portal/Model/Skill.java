package com.deep.job_portal.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "skills") @Getter @Setter @NoArgsConstructor
public class Skill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 100) private String username;
    @Column(length = 100) private String category;
}
