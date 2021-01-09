package com.josep.hris.service;

import com.josep.hris.bean.form.EmployeeForm;
import com.josep.hris.entity.Company;
import com.josep.hris.entity.Employee;
import com.josep.hris.entity.Users;
import com.josep.hris.enums.EmployeeStatusEnum;
import com.josep.hris.repository.EmployeePagingRepository;
import com.josep.hris.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeePagingRepository employeeRepository;

    @Autowired
    UserService userService;

    @Override
    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Page<Employee> findAll(Company company, Pageable pageable) {
        return employeeRepository.findByCompanyId(company, pageable);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee delete(Long id) {
        return employeeRepository.findById(id).map(employee -> {
            Users deletedUser = userService.delete(employee.getUserId().getId());
            if (deletedUser != null) {
                employee.setStatus(EmployeeStatusEnum.DELETED.getValue());
                employee.setUpdatedAt(LocalDateTime.now());
                return employeeRepository.save(employee);
            }
            return null;
        }).orElse(null);
    }

    @Override
    public Employee create(Employee employee) {
        employee.setCreatedAt(LocalDateTime.now());
        System.out.println("employee status : "+employee.getStatus());
        if (employee.getStatus() == null) {
            employee.setStatus(EmployeeStatusEnum.ACTIVE.getValue());
        }
        System.out.println("employee status 2 : "+employee.getStatus());
        return employeeRepository.save(employee);
    }

    @Override
    public Employee addEmployee(EmployeeForm employeeForm) {
        Users user = null;
        if (employeeForm.getUsername() != null) {
            user = new Users();
            user.setUsername(employeeForm.getUsername());
            user.setEmail(employeeForm.getEmail());
            user.setPassword("secret");
            user.setRoles(employeeForm.getRole());
            user.setCreatedBy(employeeForm.getCreateBy());
            user = userService.create(user);
        }
        Employee employee = new Employee();
        employee.setCompanyId(employeeForm.getCompany());
        employee.setPhone(employeeForm.getPhone());
        employee.setFullName(employeeForm.getFullName());
        employee.setCreatedBy(employeeForm.getCreateBy());
        employee.setAddress(employeeForm.getAddress());
        employee.setBarcode(employeeForm.getBarcode());
        employee.setNik(employeeForm.getNik());
        Date joinDate = employeeForm.getJoinDate() != null ? employeeForm.getJoinDate() : new Date();
        employee.setJoinDate(joinDate);
        if (user != null) {
            employee.setUserId(user);
        }
        Employee saveEmployee = create(employee);

        return saveEmployee;
    }

    @Override
    public Employee updateEmployee(Long employeeId, EmployeeForm employeeForm) {
        Employee employee = findById(employeeId);
        employee.setCompanyId(employeeForm.getCompany());
        employee.setPhone(employeeForm.getPhone());
        employee.setFullName(employeeForm.getFullName());
        employee.setCreatedBy(employeeForm.getCreateBy());
        employee.setAddress(employeeForm.getAddress());
        employee.setBarcode(employeeForm.getBarcode());
        employee.setNik(employeeForm.getNik());
        Employee saveEmployee = create(employee);

        return saveEmployee;
    }


}
