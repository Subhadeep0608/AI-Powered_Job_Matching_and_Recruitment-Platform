package com.deep.job_portal.Model;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.deep.job_portal.Model.enums.Role;
import com.deep.job_portal.Model.converters.RoleConverter;

import jakarta.persistence.*;
import lombok.*;


@Entity @Table(name = "users") @Getter @Setter @NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, unique = true, length = 150) private String username;
    @Column(nullable = false, unique = true) private String email;
    @Column(name = "password_hash", nullable = false) private String password;
    @Column(length = 30) private String phone;
    @Convert(converter = RoleConverter.class) @Column(nullable = false) private Role role = Role.CANDIDATE;
    @CreationTimestamp @Column(name = "created_at", updatable = false) private Instant createdAt;
    @UpdateTimestamp @Column(name = "updated_at") private Instant updatedAt;
}
