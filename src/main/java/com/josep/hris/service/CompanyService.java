package com.josep.hris.service;

import com.josep.hris.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {
    Company create(Company company);
    Company update(Long id, Company company);
    Page<Company> findAll(Pageable pageable);
    Iterable<Company> findAll();
}
