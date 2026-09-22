package com.deep.job_portal.Service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import com.deep.job_portal.Model.User;
import com.deep.job_portal.Model.CandidateProfile;
import com.deep.job_portal.Model.Company;
import com.deep.job_portal.Model.RecruiterProfile;
import com.deep.job_portal.Model.enums.Role;
import com.deep.job_portal.Repo.CandidateProfileRepository;
import com.deep.job_portal.Repo.CompanyRepository;
import com.deep.job_portal.Repo.RecruiterProfileRepository;
import com.deep.job_portal.Repo.UserRepo;
import com.deep.job_portal.Repo.dto.RegisterCompanyRequest;
import com.deep.job_portal.Repo.dto.RegisterRequest;
import com.deep.job_portal.Repo.dto.UpdateUserProfileRequest;
import com.deep.job_portal.Repo.dto.UserProfileResponse;

@Service 
public class userService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;
    private final CandidateProfileRepository candidateProfiles;
    private final RecruiterProfileRepository recruiterProfiles;
    private final CompanyRepository companies;

    public userService(UserRepo repo, PasswordEncoder passwordEncoder,
            CandidateProfileRepository candidateProfiles,
            RecruiterProfileRepository recruiterProfiles,
            CompanyRepository companies) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
        this.candidateProfiles = candidateProfiles;
        this.recruiterProfiles = recruiterProfiles;
        this.companies = companies;
    }
    
    public User register(RegisterRequest request) {
        Role requestedRole = request.role() == null ? Role.CANDIDATE : request.role();
        if (requestedRole == Role.ADMIN) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Users cannot register as administrators");
        }
        if (repo.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        }
        if (repo.existsByUsername(request.username())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(requestedRole);
        return repo.save(user);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile(String username) {
        User user = findUser(username);
        return UserProfileResponse.from(user,
                candidateProfiles.findByUserUsername(user.getUsername()).orElse(null),
                recruiterProfiles.findByUserUsername(user.getUsername()).orElse(null));
    }

    @Transactional
    public UserProfileResponse updateProfile(String username, UpdateUserProfileRequest request) {
        User user = findUser(username);
        String currentUsername = user.getUsername();
        if (request.username() != null && !request.username().equals(user.getUsername())) {
            if (repo.existsByUsername(request.username())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
            }
            user.setUsername(request.username());
        }
        if (request.phone() != null) {
            user.setPhone(request.phone());
        }

        if (user.getRole() == Role.CANDIDATE) {
            CandidateProfile profile = candidateProfiles.findByUserUsername(currentUsername)
                    .orElseGet(() -> {
                        CandidateProfile created = new CandidateProfile();
                        created.setUser(user);
                        return created;
                    });
            if (request.headline() != null) profile.setHeadline(request.headline());
            if (request.bio() != null) profile.setBio(request.bio());
            if (request.location() != null) profile.setLocation(request.location());
            if (request.experience() != null) profile.setExperience(request.experience());
            if (request.education() != null) profile.setEducation(request.education());
            if (request.githubUrl() != null) profile.setGithubUrl(request.githubUrl());
            if (request.linkedinUrl() != null) profile.setLinkedinUrl(request.linkedinUrl());
            if (request.portfolioUrl() != null) profile.setPortfolioUrl(request.portfolioUrl());
            if (request.candidate() != null) {
                UpdateUserProfileRequest.CandidateDetails candidate = request.candidate();
                if (candidate.headline() != null) profile.setHeadline(candidate.headline());
                if (candidate.bio() != null) profile.setBio(candidate.bio());
                if (candidate.location() != null) profile.setLocation(candidate.location());
                if (candidate.experience() != null) profile.setExperience(candidate.experience());
                if (candidate.education() != null) profile.setEducation(candidate.education());
                if (candidate.githubUrl() != null) profile.setGithubUrl(candidate.githubUrl());
                if (candidate.linkedinUrl() != null) profile.setLinkedinUrl(candidate.linkedinUrl());
                if (candidate.portfolioUrl() != null) profile.setPortfolioUrl(candidate.portfolioUrl());
            }
            candidateProfiles.save(profile);
        } else if (user.getRole() == Role.RECRUITER) {
            UpdateUserProfileRequest.CompanyDetails nestedCompany =
                    request.recruiter() == null ? null : request.recruiter().company();
            boolean hasCompanyData = request.companyName() != null
                    || request.companyDescription() != null
                    || request.companyWebsite() != null
                    || request.companyLocation() != null
                    || request.companyLogo() != null
                    || nestedCompany != null;
            String designation = request.designation() != null
                    ? request.designation()
                    : request.recruiter() == null ? null : request.recruiter().designation();

            RecruiterProfile profile = recruiterProfiles.findByUserUsername(currentUsername).orElse(null);
            if (profile == null && hasCompanyData) {
                Company company = new Company();
                applyCompanyUpdates(company, request, nestedCompany);
                if (company.getName() == null || company.getName().isBlank()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Company name is required when registering a company");
                }
                if (companies.existsByNameIgnoreCase(company.getName())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "Company name is already registered");
                }
                profile = new RecruiterProfile();
                profile.setUser(user);
                profile.setCompany(companies.save(company));
                profile.setDesignation(designation);
                recruiterProfiles.save(profile);
            } else if (profile != null) {
                if (designation != null) {
                    profile.setDesignation(designation);
                }
                if (hasCompanyData && profile.getCompany() != null) {
                    applyCompanyUpdates(profile.getCompany(), request, nestedCompany);
                    companies.save(profile.getCompany());
                }
                recruiterProfiles.save(profile);
            }
        }
        User saved = repo.save(user);
        return getProfile(saved.getUsername());
    }

    @Transactional
    public UserProfileResponse registerCompany(String username, RegisterCompanyRequest request) {
        User user = findUser(username);
        if (user.getRole() != Role.RECRUITER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Only recruiters can register a company");
        }
        if (recruiterProfiles.findByUserUsername(user.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "A company is already registered for this recruiter");
        }
        if (companies.existsByNameIgnoreCase(request.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Company name is already registered");
        }

        Company company = new Company();
        company.setName(request.name());
        company.setDescription(request.description());
        company.setWebsite(request.website());
        company.setLocation(request.location());
        company.setLogo(request.logo());
        RecruiterProfile profile = new RecruiterProfile();
        profile.setUser(user);
        profile.setCompany(companies.save(company));
        profile.setDesignation(request.designation());
        recruiterProfiles.save(profile);
        return getProfile(user.getUsername());
    }

    private User findUser(String username) {
        User user = repo.findByUsernameOrEmail(username, username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user;
    }

    private void applyCompanyUpdates(Company company, UpdateUserProfileRequest request,
            UpdateUserProfileRequest.CompanyDetails nestedCompany) {
        if (request.companyName() != null) company.setName(request.companyName());
        if (request.companyDescription() != null) company.setDescription(request.companyDescription());
        if (request.companyWebsite() != null) company.setWebsite(request.companyWebsite());
        if (request.companyLocation() != null) company.setLocation(request.companyLocation());
        if (request.companyLogo() != null) company.setLogo(request.companyLogo());
        if (nestedCompany != null) {
            if (nestedCompany.name() != null) company.setName(nestedCompany.name());
            if (nestedCompany.description() != null) company.setDescription(nestedCompany.description());
            if (nestedCompany.website() != null) company.setWebsite(nestedCompany.website());
            if (nestedCompany.location() != null) company.setLocation(nestedCompany.location());
            if (nestedCompany.logo() != null) company.setLogo(nestedCompany.logo());
        }
    }

    
}
