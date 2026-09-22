package com.deep.job_portal.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deep.job_portal.Repo.dto.CreateJobRequest;
import com.deep.job_portal.Repo.dto.JobResponse;
import com.deep.job_portal.Service.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/jobs")
public class jobController {

    private final JobService jobService;

    public jobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public List<JobResponse> listOpenJobs() {
        return jobService.listOpenJobs();
    }

    @PostMapping
    @PreAuthorize("hasRole('RECRUITER')")
    @ResponseStatus(HttpStatus.CREATED)
    public JobResponse addJob(
            @Valid @RequestBody CreateJobRequest request,
            Authentication authentication) {
        return jobService.addJob(request, authentication.getName());
    }
}
