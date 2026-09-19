package com.deep.job_portal.Service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.deep.job_portal.Model.User;
import com.deep.job_portal.Repo.UserRepo;
import com.deep.job_portal.Repo.dto.RegisterRequest;

@Service 
public class userService {

    private final UserRepo repo;
    private final PasswordEncoder passwordEncoder;

    public userService(UserRepo repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }
    
    public User register(RegisterRequest request) {
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
        return repo.save(user);
    }

    
}
