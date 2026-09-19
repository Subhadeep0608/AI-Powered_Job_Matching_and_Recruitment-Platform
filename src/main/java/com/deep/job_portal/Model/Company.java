package com.deep.job_portal.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "companies") @Getter @Setter @NoArgsConstructor
public class Company {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 200) private String name;
    @Lob private String description;
    @Column(length = 500) private String website;
    private String location;
    @Column(length = 500) private String logo;
}
