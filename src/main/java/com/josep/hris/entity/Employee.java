package com.josep.hris.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.time.LocalDateTime;

@Entity
@Data
@Where(clause = "status>=0")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @OneToOne(optional = true)
    @JoinColumn(name="user_id")
    private Users userId;

    @ManyToOne(fetch=FetchType.LAZY, optional=true)
    @JoinColumn(name="company_id")
    @JsonIgnore
    private Company companyId;

    @Size(max = 50)
    private String nik;

    @Size(max = 50)
    private String barcode;

    @NotEmpty
    @Size(min = 3, max = 255)
    private String fullName;

    @Size(max = 500)
    private String address;

    @Size(max = 20)
    private String phone;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date resignDate;

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
}
