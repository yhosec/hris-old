package com.josep.hris.service;

import com.josep.hris.entity.Role;
import com.josep.hris.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Iterable<Role> findActiveRole() {
        return roleRepository.findByStatus(1);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role delete(Long id) {
        return null;
    }

    @Override
    public Role create(Role employee) {
        return null;
    }
}
