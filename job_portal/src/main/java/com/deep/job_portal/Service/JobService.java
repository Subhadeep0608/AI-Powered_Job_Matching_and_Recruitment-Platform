package com.deep.job_portal.Service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.deep.job_portal.Model.Company;
import com.deep.job_portal.Model.Job;
import com.deep.job_portal.Model.RecruiterProfile;
import com.deep.job_portal.Model.User;
import com.deep.job_portal.Model.enums.JobStatus;
import com.deep.job_portal.Model.enums.Role;
import com.deep.job_portal.Repo.CompanyRepository;
import com.deep.job_portal.Repo.JobRepo;
import com.deep.job_portal.Repo.RecruiterProfileRepository;
import com.deep.job_portal.Repo.UserRepo;
import com.deep.job_portal.Repo.dto.CreateJobRequest;
import com.deep.job_portal.Repo.dto.JobResponse;

@Service
public class JobService {

    private final JobRepo jobRepository;
    private final CompanyRepository companyRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final UserRepo userRepository;

    public JobService(
            JobRepo jobRepository,
            CompanyRepository companyRepository,
            RecruiterProfileRepository recruiterProfileRepository,
            UserRepo userRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<JobResponse> listOpenJobs() {
        return jobRepository.findByStatusOrderByCreatedAtDesc(JobStatus.OPEN)
                .stream()
                .map(JobResponse::from)
                .toList();
    }

    @Transactional
    public JobResponse addJob(CreateJobRequest request, String username) {
        User user = userRepository.findByUsernameOrEmail(username, username);
        if (user == null || user.getRole() != Role.RECRUITER) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only recruiters can add jobs");
        }

        RecruiterProfile recruiter = recruiterProfileRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Register a company before adding jobs"));
        Company company = companyRepository.findById(request.companyId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Company not found"));

        if (!company.getId().equals(recruiter.getCompany().getId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Recruiter can only add jobs for their company");
        }
        if (request.salaryMin() != null && request.salaryMax() != null
                && request.salaryMin().compareTo(request.salaryMax()) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Minimum salary cannot exceed maximum salary");
        }
        if (request.experienceMin() != null && request.experienceMax() != null
                && request.experienceMin() > request.experienceMax()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Minimum experience cannot exceed maximum experience");
        }

        Job job = new Job();
        job.setRecruiter(recruiter);
        job.setCompany(company);
        job.setTitle(request.title());
        job.setDescription(request.description());
        job.setLocation(request.location());
        job.setSalaryMin(request.salaryMin());
        job.setSalaryMax(request.salaryMax());
        job.setExperienceMin(request.experienceMin());
        job.setExperienceMax(request.experienceMax());
        job.setEmploymentType(request.employmentType());
        job.setStatus(request.status() == null ? JobStatus.DRAFT : request.status());

        return JobResponse.from(jobRepository.save(job));
    }
}
