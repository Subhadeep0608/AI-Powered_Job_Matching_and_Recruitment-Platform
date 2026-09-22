package com.deep.job_portal.Repo.dto;

import com.deep.job_portal.Model.CandidateProfile;
import com.deep.job_portal.Model.Company;
import com.deep.job_portal.Model.RecruiterProfile;
import com.deep.job_portal.Model.User;

public record UserProfileResponse(
        Long id,
        String username,
        String email,
        String phone,
        String role,
        CandidateDetails candidate,
        RecruiterDetails recruiter) {

    public static UserProfileResponse from(User user, CandidateProfile candidate, RecruiterProfile recruiter) {
        return new UserProfileResponse(
                user.getId(), user.getUsername(), user.getEmail(), user.getPhone(), user.getRole().name(),
                candidate == null ? CandidateDetails.empty() : CandidateDetails.from(candidate),
                recruiter == null ? RecruiterDetails.empty() : RecruiterDetails.from(recruiter));
    }

    public record CandidateDetails(
            Long id, String headline, String bio, String location, String experience,
            String education, String githubUrl, String linkedinUrl, String portfolioUrl) {
        static CandidateDetails from(CandidateProfile profile) {
            return new CandidateDetails(profile.getId(), profile.getHeadline(), profile.getBio(),
                    profile.getLocation(), profile.getExperience(), profile.getEducation(),
                    profile.getGithubUrl(), profile.getLinkedinUrl(), profile.getPortfolioUrl());
        }

        static CandidateDetails empty() {
            return new CandidateDetails(null, null, null, null, null, null, null, null, null);
        }
    }

    public record RecruiterDetails(Long id, String designation, CompanyDetails company) {
        static RecruiterDetails from(RecruiterProfile profile) {
            return new RecruiterDetails(profile.getId(), profile.getDesignation(),
                    profile.getCompany() == null ? CompanyDetails.empty() : CompanyDetails.from(profile.getCompany()));
        }

        static RecruiterDetails empty() {
            return new RecruiterDetails(null, null, CompanyDetails.empty());
        }
    }

    public record CompanyDetails(Long id, String name, String description, String website,
            String location, String logo) {
        static CompanyDetails from(Company company) {
            return new CompanyDetails(company.getId(), company.getName(), company.getDescription(),
                    company.getWebsite(), company.getLocation(), company.getLogo());
        }

        static CompanyDetails empty() {
            return new CompanyDetails(null, null, null, null, null, null);
        }
    }
}
