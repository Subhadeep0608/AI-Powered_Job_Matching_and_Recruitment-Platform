package com.deep.job_portal.Repo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank String username,
        @NotBlank @Size(max = 72) String password
) {}
