    package com.deep.job_portal.Repo;

import com.deep.job_portal.Model.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CandidateProfileRepository
        extends JpaRepository<CandidateProfile, Long> {

    Optional<CandidateProfile> findByUserUsername(String username);
}