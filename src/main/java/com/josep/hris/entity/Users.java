package com.josep.hris.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Where(clause = "status>=0")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @NotEmpty
    @Size(max = 255)
    @Column(unique = true)
    private String username;

    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;

    @Size(min = 6)
    private String password;

    private Integer status;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne(fetch=FetchType.LAZY, optional=true)
    @JoinColumn(name="created_by")
    @JsonIgnore
    private Users createdBy;

    @ManyToOne(fetch=FetchType.LAZY, optional=true)
    @JoinColumn(name="updated_by")
    @JsonIgnore
    private Users updatedBy;

    @ManyToOne(fetch=FetchType.LAZY, optional=true)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role roles;

    @OneToOne(mappedBy = "userId")
    private Employee employee;

    public Company getCompany(Users user) {
        Company company = new Company();
        if (user != null && user.getEmployee() != null && user.getEmployee().getCompanyId() != null) {
            company = user.getEmployee().getCompanyId();
        }
        return company;
    }
}
