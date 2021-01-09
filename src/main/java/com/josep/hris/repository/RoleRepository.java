package com.josep.hris.repository;

import com.josep.hris.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Iterable<Role> findByStatus(Integer status);
}
