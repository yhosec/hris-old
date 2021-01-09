package com.josep.hris.service;

import com.josep.hris.bean.form.EmployeeForm;
import com.josep.hris.entity.Company;
import com.josep.hris.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    Iterable<Employee> findAll();
    Page<Employee> findAll(Company company, Pageable var1);
    Employee findById(Long id);
    Employee delete(Long id);
    Employee create(Employee employee);

    Employee addEmployee(EmployeeForm employeeForm);
    Employee updateEmployee(Long employeeId, EmployeeForm employeeForm);
}
