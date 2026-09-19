package com.deep.job_portal.Repo.dto;

import java.math.BigDecimal;

import com.deep.job_portal.Model.enums.EmploymentType;
import com.deep.job_portal.Model.enums.JobStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateJobRequest(
        @NotNull Long companyId,
        @NotBlank @Size(max = 255) String title,
        @NotBlank String description,
        @Size(max = 255) String location,
        @DecimalMin(value = "0.0", inclusive = true) BigDecimal salaryMin,
        @DecimalMin(value = "0.0", inclusive = true) BigDecimal salaryMax,
        @PositiveOrZero Integer experienceMin,
        @PositiveOrZero Integer experienceMax,
        @NotNull EmploymentType employmentType,
        JobStatus status) {
}
