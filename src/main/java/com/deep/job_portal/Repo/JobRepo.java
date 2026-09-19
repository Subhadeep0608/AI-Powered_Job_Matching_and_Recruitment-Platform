package com.deep.job_portal.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deep.job_portal.Model.Job;
import com.deep.job_portal.Model.enums.JobStatus;

public interface JobRepo extends JpaRepository<Job, Long> {
    List<Job> findByStatusOrderByCreatedAtDesc(JobStatus status);
}