package com.deep.job_portal.Repo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterCompanyRequest(
        @NotBlank @Size(max = 200) String name,
        String description,
        @Size(max = 500) String website,
        @Size(max = 255) String location,
        @Size(max = 500) String logo,
        @Size(max = 150) String designation) {
}
