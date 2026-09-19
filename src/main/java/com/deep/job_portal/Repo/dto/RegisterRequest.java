// RegisterRequest.java
package com.deep.job_portal.Repo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank @Size(max = 150) String username,
    @NotBlank @Email String email,
    @NotBlank @Size(min = 4, max = 72) String password
) {}
