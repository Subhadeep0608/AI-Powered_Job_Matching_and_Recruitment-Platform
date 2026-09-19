package com.deep.job_portal.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deep.job_portal.Model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
