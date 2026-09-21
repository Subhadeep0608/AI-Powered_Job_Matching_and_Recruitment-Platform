package com.deep.job_portal.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deep.job_portal.Repo.dto.RegisterCompanyRequest;
import com.deep.job_portal.Repo.dto.UpdateUserProfileRequest;
import com.deep.job_portal.Repo.dto.UserProfileResponse;
import com.deep.job_portal.Service.userService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final userService userService;

    public UserProfileController(userService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserProfileResponse getProfile(Authentication authentication) {
        return userService.getProfile(authentication.getName());
    }

    @PutMapping
    public UserProfileResponse updateProfile(
            @Valid @RequestBody UpdateUserProfileRequest request,
            Authentication authentication) {
        return userService.updateProfile(authentication.getName(), request);
    }

    @PostMapping("/company")
    @PreAuthorize("hasRole('RECRUITER')")
    @ResponseStatus(HttpStatus.CREATED)
    public UserProfileResponse registerCompany(
            @Valid @RequestBody RegisterCompanyRequest request,
            Authentication authentication) {
        return userService.registerCompany(authentication.getName(), request);
    }
}
