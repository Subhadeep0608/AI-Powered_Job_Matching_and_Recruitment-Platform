package com.deep.job_portal.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deep.job_portal.Model.User;

@Repository
public interface UserRepo extends JpaRepository<User , Long>  {

    User findByUsernameOrEmail(String username, String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
    
}
