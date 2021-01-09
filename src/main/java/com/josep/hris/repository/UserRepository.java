package com.josep.hris.repository;

import com.josep.hris.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    @Query(value = "select u from Users u inner join fetch u.roles where u.username =:username and u.status = :status")
    Users findByUsernameAndStatus(
        @Param("username") String username,
        @Param("status") Integer status
    );
    Users findByEmailAndStatus(String email, Integer status);
}
