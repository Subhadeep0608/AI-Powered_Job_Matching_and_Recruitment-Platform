    package com.deep.job_portal.Repo;

import com.deep.job_portal.Model.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateProfileRepository
        extends JpaRepository<CandidateProfile, Long> {
}