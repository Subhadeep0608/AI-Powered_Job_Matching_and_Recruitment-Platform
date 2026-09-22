package com.deep.job_portal.Repo.dto;

import jakarta.validation.constraints.Size;

public record UpdateUserProfileRequest(
        @Size(max = 30) String phone,
        @Size(max = 150) String username,
        @Size(max = 500) String headline,
        String bio,
        @Size(max = 255) String location,
        String experience,
        String education,
        @Size(max = 500) String githubUrl,
        @Size(max = 500) String linkedinUrl,
        @Size(max = 500) String portfolioUrl,
        @Size(max = 150) String designation,
        @Size(max = 200) String companyName,
        String companyDescription,
        @Size(max = 500) String companyWebsite,
        @Size(max = 255) String companyLocation,
        @Size(max = 500) String companyLogo,
        CandidateDetails candidate,
        RecruiterDetails recruiter) {

    public record CandidateDetails(
            String headline,
            String bio,
            String location,
            String experience,
            String education,
            String githubUrl,
            String linkedinUrl,
            String portfolioUrl) {
    }

    public record RecruiterDetails(
            String designation,
            CompanyDetails company) {
    }

    public record CompanyDetails(
            String name,
            String description,
            String website,
            String location,
            String logo) {
    }
}
