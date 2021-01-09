package com.josep.hris.repository;

import com.josep.hris.entity.Company;
import com.josep.hris.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeePagingRepository extends PagingAndSortingRepository<Employee, Long> {
    Page<Employee> findByCompanyId(Company companyId, Pageable pageable);
}
