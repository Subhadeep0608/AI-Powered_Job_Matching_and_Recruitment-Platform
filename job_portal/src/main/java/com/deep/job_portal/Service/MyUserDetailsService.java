package com.deep.job_portal.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deep.job_portal.Model.User;
import com.deep.job_portal.Repo.UserPrincipal;
import com.deep.job_portal.Repo.UserRepo;



@Service
public class MyUserDetailsService implements UserDetailsService{

    private final UserRepo repo;

    public MyUserDetailsService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
       User user = repo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
       if(user==null){
        throw new UsernameNotFoundException("User not found");
       }

       return new UserPrincipal(user);

    }

    
}
