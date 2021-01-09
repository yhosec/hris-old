package com.josep.hris.service;

import com.josep.hris.bean.form.RegistrationForm;
import com.josep.hris.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    Users findById(Long id);
    Users create(Users users);
    Users delete(Long id);
    void register(RegistrationForm registrationForm);
    Users findByUsername(String username);
    Users findByEmail(String email);
}
