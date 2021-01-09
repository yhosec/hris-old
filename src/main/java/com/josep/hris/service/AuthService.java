package com.josep.hris.service;

import com.josep.hris.entity.Users;

import java.security.Principal;

public interface AuthService {
    public Users getIdentity(Principal principal);
}
