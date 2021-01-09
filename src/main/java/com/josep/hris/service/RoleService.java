package com.josep.hris.service;

import com.josep.hris.entity.Role;

public interface RoleService {
    Iterable<Role> findAll();
    Iterable<Role> findActiveRole();
    Role findById(Long id);
    Role delete(Long id);
    Role create(Role employee);
}
