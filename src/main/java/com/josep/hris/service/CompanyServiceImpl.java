package com.josep.hris.service;

import com.josep.hris.entity.Company;
import com.josep.hris.enums.CompanySetupInitialDataEnum;
import com.josep.hris.enums.CompanyStatusEnum;
import com.josep.hris.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Override
    public Company create(Company company) {
        if (company.getStatus() == null) {
            company.setStatus(CompanyStatusEnum.ACTIVE.getValue());
        }

        if (company.getSetupInitialData() == null) {
            company.setSetupInitialData(CompanySetupInitialDataEnum.NOT_YET.getValue());
        }

        company.setCreatedAt(LocalDateTime.now());
        return companyRepository.save(company);
    }

    @Override
    public Company update(Long id, Company company) {
        return null;
    }

    @Override
    public Page<Company> findAll(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    public Iterable<Company> findAll() {
        return companyRepository.findAll();
    }
}
