package com.josep.hris.service;

import com.josep.hris.bean.form.RegistrationForm;
import com.josep.hris.entity.Company;
import com.josep.hris.entity.Employee;
import com.josep.hris.entity.Users;
import com.josep.hris.enums.RoleEnum;
import com.josep.hris.enums.UsersStatusEnum;
import com.josep.hris.repository.CompanyRepository;
import com.josep.hris.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Users findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Users create(Users users) {
        users.setCreatedAt(LocalDateTime.now());
        if (users.getPassword() != null)
            users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));

        if (users.getStatus() == null) {
            users.setStatus(UsersStatusEnum.ACTIVE.getValue());
        }

        return userRepository.save(users);
    }

    @Override
    public Users delete(Long id) {
        return userRepository.findById(id).map(user -> {
            user.setStatus(UsersStatusEnum.DELETED.getValue());
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        }).orElse(null);
    }

    @Override
    public void  register(RegistrationForm registrationForm) {
        Users users = new Users();
        users.setUsername(registrationForm.getUsername());
        users.setPassword(registrationForm.getPassword());
        users.setEmail(registrationForm.getEmail());
        users.setRoles(roleService.findById(RoleEnum.ADMIN.getValue()));
        Users user = create(users);

        if (user != null) {
            System.out.println("new ID : " + user.getId());
            Company company = new Company();
            company.setName(registrationForm.getCompanyName());
            company.setCreatedBy(user);
            company = companyService.create(company);

            if (company != null) {
                Employee employee = new Employee();
                employee.setFullName(registrationForm.getFullName());
                employee.setPhone(registrationForm.getPhone());
                employee.setUserId(user);
                employee.setCompanyId(company);
                employee.setCreatedBy(user);

                employee = employeeService.create(employee);
            }

        }

    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsernameAndStatus(username, UsersStatusEnum.ACTIVE.getValue());
    }

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UsersStatusEnum.ACTIVE.getValue());
    }

}
