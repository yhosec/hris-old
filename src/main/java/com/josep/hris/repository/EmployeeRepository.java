package com.josep.hris.repository;

import com.josep.hris.entity.Company;
import com.josep.hris.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByNikAndFullName(String nik, String fullName);
}
