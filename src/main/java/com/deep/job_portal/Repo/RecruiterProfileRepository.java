package com.deep.job_portal.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deep.job_portal.Model.RecruiterProfile;

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfile, Long> {
    Optional<RecruiterProfile> findByUserUsername(String username);
}
