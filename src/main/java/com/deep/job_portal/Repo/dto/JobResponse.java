package com.deep.job_portal.Repo.dto;

import java.math.BigDecimal;
import java.time.Instant;

import com.deep.job_portal.Model.Job;
import com.deep.job_portal.Model.enums.EmploymentType;

public record JobResponse(
        Long id,
        String title,
        String companyName,
        String companyLocation,
        String location,
        BigDecimal salaryMin,
        BigDecimal salaryMax,
        Integer experienceMin,
        Integer experienceMax,
        EmploymentType employmentType,
        String description,
        Instant createdAt) {

    public static JobResponse from(Job job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getCompany().getName(),
                job.getCompany().getLocation(),
                job.getLocation(),
                job.getSalaryMin(),
                job.getSalaryMax(),
                job.getExperienceMin(),
                job.getExperienceMax(),
                job.getEmploymentType(),
                job.getDescription(),
                job.getCreatedAt());
    }
}
