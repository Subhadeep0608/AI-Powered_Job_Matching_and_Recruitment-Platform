package com.deep.job_portal.Controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.deep.job_portal.Model.User;
import com.deep.job_portal.Service.JwtService;
import com.deep.job_portal.Service.userService;
import com.deep.job_portal.Repo.dto.LoginRequest;
import com.deep.job_portal.Repo.dto.RegisterRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping ("api/auth") 
public class userController {
    private final AuthenticationManager authenticationManager;
    private final userService service;
    private final JwtService jwtService;

    userController(AuthenticationManager authenticationManager, userService service, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.service = service;
        this.jwtService = jwtService;
    }
    
    @PostMapping ("register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> register(@Valid @RequestBody RegisterRequest request){
        User savedUser = service.register(request);

        return Map.of(
            "message", "Registration successful",
            "userId", savedUser.getId(),
            "email", savedUser.getEmail()
        );
    }

    @PostMapping ("login")
    public Map<String, String> login(@Valid @RequestBody LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        if(authentication.isAuthenticated()){
            return Map.of("token", jwtService.generateToken(authentication.getName()));
        }else{
            throw new org.springframework.security.authentication.BadCredentialsException("Invalid username or password");
        }
    }

}
