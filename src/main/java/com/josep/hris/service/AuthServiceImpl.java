package com.josep.hris.service;

import com.josep.hris.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    UserService userService;

    @Override
    @Cacheable("identity")
    public Users getIdentity(Principal principal) {
        return userService.findByUsername(principal.getName());
    }
}
